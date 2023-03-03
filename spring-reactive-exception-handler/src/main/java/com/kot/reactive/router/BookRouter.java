package com.kot.reactive.router;

import com.kot.reactive.handler.BookHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BookRouter {

    @Autowired
    private BookHandler bookHandler;

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }

    @Bean
    public RouterFunction<ServerResponse> routeBook(){
        return RouterFunctions.route()
                .GET("/route/book",bookHandler::getBooks)
                .GET("/route/book/{id}",bookHandler::getBook)
                .build();
    }
}
