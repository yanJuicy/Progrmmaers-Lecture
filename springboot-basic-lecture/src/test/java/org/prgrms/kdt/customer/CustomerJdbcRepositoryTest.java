package org.prgrms.kdt.customer;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.OrderTester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.config.Charset.UTF8;

@SpringJUnitConfig
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerJdbcRepositoryTest {

  @Configuration
  @ComponentScan(
    basePackages = {"org.prgrms.kdt.customer"}
  )
  static class Config {

    @Bean
    public DataSource dataSource() {
      var dataSource = DataSourceBuilder.create()
        .url("jdbc:mysql://localhost:2215/test-order_mgmt")
        .username("test")
        .password("test1234!")
        .type(HikariDataSource.class)
        .build();
      dataSource.setMaximumPoolSize(1000);
      dataSource.setMinimumIdle(100);
      return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
      return new JdbcTemplate(dataSource);
    }

  }

  @Autowired
  CustomerJdbcRepository customerJdbcRepository;

  @Autowired
  DataSource dataSource;

  Customer newCustomer;

  EmbeddedMysql embeddedMysql;

  @BeforeAll
  void setup() {
    newCustomer = new Customer(UUID.randomUUID(), "test-user", "test-user@gmail.com", LocalDateTime.now());
    var mysqlConfig = aMysqldConfig(v8_0_11)
      .withCharset(UTF8)
      .withPort(2215)
      .withUser("test", "test1234!")
      .withTimeZone("Asia/Seoul")
      .build();
    embeddedMysql = anEmbeddedMysql(mysqlConfig)
      .addSchema("test-order_mgmt", classPathScript("schema.sql"))
      .start();
//    customerJdbcRepository.deleteAll();
  }

  @AfterAll
  void cleanup() {
    embeddedMysql.stop();
  }

  @Test
  @Order(1)
  public void testHikariConnectionPool() {
    assertThat(dataSource.getClass().getName(), is("com.zaxxer.hikari.HikariDataSource"));
  }

  @Test
  @Order(2)
  @DisplayName("????????? ????????? ??? ??????.")
  public void testInsert() {

    try {
      customerJdbcRepository.insert(newCustomer);
    } catch (BadSqlGrammarException e) {
      e.getSQLException().getErrorCode();
    }


    var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
  }

  @Test
  @Order(3)
  @DisplayName("?????? ????????? ????????? ??? ??????.")
  public void testFindAll() {
    var customers = customerJdbcRepository.findAll();
    assertThat(customers.isEmpty(), is(false));
  }

  @Test
  @Order(4)
  @DisplayName("???????????? ????????? ????????? ??? ??????.")
  public void testFindByName() {
    var customer = customerJdbcRepository.findByName(newCustomer.getName());
    assertThat(customer.isEmpty(), is(false));

    var unknown = customerJdbcRepository.findByName("unknown-user");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @Order(5)
  @DisplayName("???????????? ????????? ????????? ??? ??????.")
  public void testFindByEmail() {
    var customer = customerJdbcRepository.findByEmail(newCustomer.getEmail());
    assertThat(customer.isEmpty(), is(false));

    var unknown = customerJdbcRepository.findByEmail("unknown-user@gmail.com");
    assertThat(unknown.isEmpty(), is(true));
  }

  @Test
  @Order(6)
  @DisplayName("????????? ????????? ??? ??????.")
  public void testUpdate() {
    newCustomer.changeName("updated-user");
    customerJdbcRepository.update(newCustomer);

    var all = customerJdbcRepository.findAll();
    assertThat(all, hasSize(1));
    assertThat(all, everyItem(samePropertyValuesAs(newCustomer)));

    var retrievedCustomer = customerJdbcRepository.findById(newCustomer.getCustomerId());
    assertThat(retrievedCustomer.isEmpty(), is(false));
    assertThat(retrievedCustomer.get(), samePropertyValuesAs(newCustomer));
  }


}