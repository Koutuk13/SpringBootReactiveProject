package com.kot.springbootwebfluxdemo.router;

import com.kot.springbootwebfluxdemo.handler.CustomerHandler;
import com.kot.springbootwebfluxdemo.handler.CustomerStreamHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Autowired
    private CustomerHandler customerHandler;

    @Autowired
    private CustomerStreamHandler customerStreamHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/customers",customerHandler:: loadCustomers)
                .GET("/router/stream/customers", customerStreamHandler:: loadCustomerStream)
                .GET("/router/customers/{input}",customerHandler::findCustomer)
                .POST("/router/customers/save", customerHandler::saveCustomer)
                .build();
    }
}
