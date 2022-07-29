package com.example.contractprocessingservice;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContractProcessingServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(ContractProcessingServiceApplication.class, args);
    }

}
