package com.example.contractprocessingservice.mapper;

import com.example.contractprocessingservice.model.ContractDto;
import com.example.contractprocessingservice.model.ContractualParty;
import com.example.contractprocessingservice.model.CreateNewContract;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContractMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "dateStart", target = "dateStart"),
            @Mapping(source = "dateEnd", target = "dateEnd"),
            @Mapping(target = "dateSend", expression = "java(dateMap(newContract.getDateSend()))"),
            @Mapping(source = "contractNumber", target = "contractNumber"),
            @Mapping(source = "contractName", target = "contractName"),
            @Mapping(source = "clientApi", target = "clientApi"),
            @Mapping(target = "contractualParties", expression = "java(partiesToString(newContract.getContractualParties()))")
    })
    ContractDto toContractDto(CreateNewContract newContract);

    default Date dateMap(LocalDateTime dateTime){
        return Date.valueOf(dateTime.toLocalDate());
    }

    default String partiesToString(List<ContractualParty> parties){
        ObjectMapper objectMapper = new ObjectMapper();
        String json;
        try {
            json = objectMapper.writeValueAsString(parties);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
