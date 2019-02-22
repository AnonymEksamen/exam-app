package com.example.herokupipeexample;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);
    Customer deleteByLastName(String lastName);
    void deleteAll();
}
