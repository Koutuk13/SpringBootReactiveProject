package com.kot.springbootwebfluxdemo.handler;

import com.kot.springbootwebfluxdemo.dao.CustomerDAO;
import com.kot.springbootwebfluxdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {

    @Autowired
    private CustomerDAO customerDAO;

    public Mono<ServerResponse> loadCustomers(ServerRequest serverRequest){
        Flux<Customer> customerRouterDetails = customerDAO.getCustomerRouterDetails();
       return ServerResponse.ok().body(customerRouterDetails,Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest serverRequest){
        int customerId = Integer.parseInt(serverRequest.pathVariable("input"));
        Mono<Customer> customerMono = customerDAO.getCustomerRouterDetails()
                .filter(customer -> customer.getId() == customerId)
                .next();
        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest serverRequest){
        Mono<Customer> customerMono = serverRequest.bodyToMono(Customer.class);
        Mono<String> response = customerMono.map(customer -> customer.getId() + " :: " + customer.getName());
        return ServerResponse.ok().body(response, String.class);
    }
}
