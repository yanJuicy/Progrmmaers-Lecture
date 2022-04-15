package org.prgms.kdt;

import org.prgms.kdt.customer.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JdbcCustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final String SELECTS_SQL = "select * from customers WHERE name = ?";
    private final String SELECTS_ALL_SQL = "select * from customers";
    private final String INSERT_SQL = "insert into customers(customer_id, name, email) values (uuid_to_bin(?), ?, ?)";
    private final String DELETE_ALL_SQL = "DELETE FROM customers";
    private final String UPDATE_BY_ID_SQL = "update customers set name = ? where customer_id = uuid_to_bin(?)";

    public List<String> findNames(String name) {
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "gkswn14520");
                var statement = connection.prepareStatement(SELECTS_SQL);
                var resultSet = statement.executeQuery()
        ) {
            statement.setString(1, name);
            logger.info("statement -> {}", statement);
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return names;
    }

    public List<String> findAllNames() {
        List<String> names = new ArrayList<>();

        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "gkswn14520");
                var statement = connection.prepareStatement(SELECTS_ALL_SQL);
                var resultSet = statement.executeQuery()
        ) {
            logger.info("statement -> {}", statement);
            while (resultSet.next()) {
                var customerName = resultSet.getString("name");
                var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                logger.info("customer id -> {}, name -> {}, createdAt -> {}", customerId, customerName, createdAt);
                names.add(customerName);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return names;
    }

    public int insertCustomer(UUID customerId, String name, String email) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "gkswn14520");
                var statement = connection.prepareStatement(INSERT_SQL);
        ) {
            statement.setBytes(1, customerId.toString().getBytes());
            statement.setString(2, name);
            statement.setString(3, email);
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int updateCustomer(UUID customerId, String name) {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "gkswn14520");
                var statement = connection.prepareStatement(UPDATE_BY_ID_SQL);
        ) {
            statement.setString(1, name);
            statement.setBytes(2, customerId.toString().getBytes());
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public int deleteAllCustomers() {
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "gkswn14520");
                var statement = connection.prepareStatement(DELETE_ALL_SQL);
        ) {
            return statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return 0;
    }

    public void transactionTest(Customer customer) {
        String updateNameSql = "update customers set name = ? where customer_id =  uuid_to_bin(?)";
        String updateEmailSql = "update customers set email = ? where customer_id =  uuid_to_bin(?)";

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "gkswn14520");
            connection.setAutoCommit(false);

            try (
                    var updateNameStatement = connection.prepareStatement(updateNameSql);
                    var updateEmailStatement = connection.prepareStatement(updateEmailSql);
            ) {
                updateNameStatement.setString(1, customer.getName());
                updateNameStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateNameStatement.executeUpdate();

                updateEmailStatement.setString(1, customer.getEmail());
                updateEmailStatement.setBytes(2, customer.getCustomerId().toString().getBytes());
                updateEmailStatement.executeUpdate();
                connection.setAutoCommit(true);
            } catch (SQLException throwable) {
                logger.error("Got error while closing connection ", throwable);
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var customerRepository = new JdbcCustomerRepository();

        customerRepository.transactionTest(
                new Customer(UUID.fromString("399145e3-bb9b-11ec-b247-0242ac110002"), "update-user",
                        "new-user2@gmail.com", LocalDateTime.now())
        );

//        var count = customerRepository.deleteAllCustomers();
//        logger.info("delete count -> {}", count);
//
//        customerRepository.insertCustomer(UUID.randomUUID(), "new-user", "new-user@gmail.com");
//        var customer2 = UUID.randomUUID();
//        customerRepository.insertCustomer(customer2, "new-user2", "new-user2@gmail.com");
//
//        customerRepository.updateCustomer(customer2, "update-user2");
//
//        var names = new JdbcCustomerRepository().findAllNames();
//        names.forEach(v -> logger.info("Found name: {}", v));
    }

}
