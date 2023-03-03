package com.kot.springbootwebfluxdemo.handler;

import com.kot.springbootwebfluxdemo.dao.CustomerDAO;
import com.kot.springbootwebfluxdemo.dto.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {

    @Autowired
    private CustomerDAO customerDAO;

    public Mono<ServerResponse> loadCustomerStream(ServerRequest serverRequest){
        Flux<Customer> customerStreamDetails = customerDAO.getCustomerStreamDetails();
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(customerStreamDetails,Customer.class);
    }

}
