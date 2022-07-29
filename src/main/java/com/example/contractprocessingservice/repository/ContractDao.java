package com.example.contractprocessingservice.repository;

import com.example.contractprocessingservice.mapper.ContractDtoMapper;
import com.example.contractprocessingservice.model.ContractDto;
import lombok.Lombok;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ContractDao {

    private static Logger logger = LoggerFactory.getLogger(ContractDao.class);

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private ContractDtoMapper contractDtoMapper;

    private String SQL_GET_CONTRACT_BY_ID = "SELECT * FROM contract WHERE ID = :id";

    private String SQL_GET_CONTRACT_BY_NUMBER = "SELECT * FROM contract WHERE contract_number LIKE :contract_number";

    private String INSERT_SQL = "INSERT INTO contract" +
            "(id, date_start, date_end, date_send, contract_number, contract_name, client_api, contractual_parties)" +
            "VALUES ( :id, :dateStart, :dateEnd, :dateSend, :contractNumber, :contractName, :clientApi, :contractualParties::json) " +
            "RETURNING date_create";

    @Autowired
    public ContractDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, ContractDtoMapper contractDtoMapper){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.contractDtoMapper = contractDtoMapper;
    }

    public LocalDateTime create(final ContractDto contract) {
        Map<String, Object> parameters = createNamedParameters(contract);
        LocalDateTime dateCreate = namedParameterJdbcTemplate.queryForObject(INSERT_SQL, parameters, LocalDateTime.class);
        return dateCreate;
    }


    private Map<String, Object> createNamedParameters(ContractDto contract) throws DataAccessException {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", contract.getId());
        namedParameters.put("dateStart", contract.getDateStart());
        namedParameters.put("dateEnd", contract.getDateEnd());
        namedParameters.put("dateSend", contract.getDateSend());
        namedParameters.put("contractNumber", contract.getContractNumber());
        namedParameters.put("contractName", contract.getContractName());
        namedParameters.put("clientApi", contract.getClientApi());
        namedParameters.put("contractualParties", contract.getContractualParties());
        return namedParameters;
    }

    public Optional<ContractDto> getContractById(UUID id) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("id", id);
        try {
            Optional<ContractDto> optional = Optional.of(namedParameterJdbcTemplate
                    .queryForObject(SQL_GET_CONTRACT_BY_ID, namedParameters, contractDtoMapper));
            return optional;
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<ContractDto> getContractByContractNumber(String contractNumber) {
        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("contract_number", contractNumber);
        try {
            Optional<ContractDto> optional = Optional.of(namedParameterJdbcTemplate
                    .queryForObject(SQL_GET_CONTRACT_BY_NUMBER, namedParameters, contractDtoMapper));
            return optional;
        }catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
