package com.kot.springbootwebfluxdemo.service;

import com.kot.springbootwebfluxdemo.dao.CustomerDAO;
import com.kot.springbootwebfluxdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    public List<Customer> loadAllCustomers(){

        long start = System.currentTimeMillis();
        List<Customer> customerDetails = customerDAO.getCustomerDetails();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time :: " + (end -start));
        return customerDetails;
    }

    public Flux<Customer> loadAllCustomersStream(){

        long start = System.currentTimeMillis();
        Flux<Customer> customerDetails = customerDAO.getCustomerStreamDetails();
        long end = System.currentTimeMillis();
        System.out.println("Total execution time :: " + (end -start));
        return customerDetails;
    }
}
