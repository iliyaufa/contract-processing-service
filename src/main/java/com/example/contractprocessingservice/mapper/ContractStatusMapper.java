package com.example.contractprocessingservice.mapper;

import com.example.contractprocessingservice.model.ContractStatus;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.UUID;
@Component
public class ContractStatusMapper implements RowMapper<ContractStatus> {
    @Override
    public ContractStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
        ContractStatus status = new ContractStatus();
        status.setId((UUID) rs.getObject("id"));
        System.out.println(status.toString());
        status.setDateCreate(rs.getDate("date_create").toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        System.out.println(status.toString());
        status.setStatus(ContractStatus.Status.CREATED);
        System.out.println(status.toString());
        return status;
    }
}
