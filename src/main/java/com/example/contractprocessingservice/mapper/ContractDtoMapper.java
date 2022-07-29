package com.example.contractprocessingservice.mapper;

import com.example.contractprocessingservice.model.ContractDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class ContractDtoMapper implements RowMapper<ContractDto> {
    @Override
    public ContractDto mapRow(ResultSet rs, int numRow) throws SQLException {

        ContractDto contract = new ContractDto();
        contract.setId((UUID) rs.getObject("id"));
        contract.setDateStart(rs.getDate("date_start"));
        contract.setDateEnd(rs.getDate("date_end"));
        contract.setDateSend(rs.getDate("date_send"));
        contract.setContractNumber(rs.getString("contract_number"));
        contract.setContractName(rs.getString("contract_name"));
        contract.setClientApi(rs.getString("client_api"));
        contract.setContractualParties(rs.getString("contractual_parties"));
        return contract;
    }
}
