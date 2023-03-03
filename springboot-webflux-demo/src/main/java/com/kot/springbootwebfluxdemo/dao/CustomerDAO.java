package com.kot.springbootwebfluxdemo.dao;

import com.kot.springbootwebfluxdemo.dto.Customer;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class CustomerDAO {

    private static void sleepExecution(int i){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getCustomerDetails(){
        return IntStream.rangeClosed(1,50)
                .peek(CustomerDAO::sleepExecution)
                .peek(i->System.out.println("processing count is :: "+ i))
                .mapToObj(i->new Customer(i, "customer"+i))
                .collect(Collectors.toList());
    }

    public Flux<Customer> getCustomerStreamDetails(){
        return  Flux.range(1,10)
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(i->System.out.println("processing count in stream flow:: "+ i))
                .map(i->new Customer(i, "customer"+i));

    }

    public Flux<Customer> getCustomerRouterDetails(){
        return  Flux.range(1,10)
                .doOnNext(i->System.out.println("processing count in stream flow:: "+ i))
                .map(i->new Customer(i, "customer"+i));

    }


}
