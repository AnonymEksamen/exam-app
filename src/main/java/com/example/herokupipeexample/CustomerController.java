package com.example.herokupipeexample;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    private CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
      this.customerRepository = customerRepository;
    }

    @RequestMapping("/")
    public String welcome() {
        return "Det funker!";
    }

    @RequestMapping("/list")
    public List<Customer> find(@RequestParam(value="lastName") String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    @PostMapping("/")
    public Customer newCustomer(@RequestBody Customer customer) {
        System.out.println(customer);
    		return customerRepository.save(customer);
    }

    @DeleteMapping("/")
    public Customer deleteCustomer(@RequestParam(value="lastName")String lastName) {
        return customerRepository.deleteByLastName(lastName);
    }
}
