package com.programmers.programmersjpalecture.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CustomerTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void test() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("hong");
        customer.setLastName("kang");

        customerRepository.save(customer);

        Customer entity = customerRepository.findById(1L).get();
        log.info("{} {}", entity.getLastName(), entity.getFirstName());
    }

}