package com.example.contractprocessingservice.routes;

import com.example.contractprocessingservice.mapper.ContractMapper;
import com.example.contractprocessingservice.mapper.ContractMapperImpl;
import com.example.contractprocessingservice.model.ContractDto;
import com.example.contractprocessingservice.model.ContractProcessingService;
import com.example.contractprocessingservice.model.ContractStatus;
import com.example.contractprocessingservice.model.CreateNewContract;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class ContractProcessor implements Processor {

    private final ContractProcessingService contractProcessingService;

    private final ContractMapper contractMapper;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ContractProcessor(ContractProcessingService contractProcessingService, ContractMapperImpl contractMapper) {
        this.contractProcessingService = contractProcessingService;
        this.contractMapper = contractMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getIn().getBody(String.class);
        mapperSetUp(this.mapper);
        CreateNewContract contract = mapper.readValue(message, CreateNewContract.class);
        ContractDto contractDto = contractMapper.toContractDto(contract);
        ContractStatus status = contractProcessingService.contractHandle(contractDto);
        String statusStr = mapper.writeValueAsString(status);
        exchange.getOut().setBody(statusStr, String.class);
    }

    private ObjectMapper mapperSetUp(ObjectMapper mapper) {
        mapper.registerModule(new JavaTimeModule());
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }
}
