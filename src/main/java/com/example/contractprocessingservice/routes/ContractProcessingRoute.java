package com.example.contractprocessingservice.routes;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
public class ContractProcessingRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("rabbitmq://localhost:5673/contract.create?queue=contract.create&routingKey=soap&autoDelete=false")
                .streamCaching()
                .log("before processing " + "${body}")
                .process("contractProcessor")
                .log("after processing " + "${body}")
                .to("rabbitmq://localhost:5673/contract.event?queue=contract.event&routingKey=soap&autoDelete=false").log("${body}");
    }
}

