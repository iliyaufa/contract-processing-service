package com.example.contractprocessingservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ContractDto {

    private UUID id;
    private Date dateStart;
    private Date dateEnd;
    private Date dateSend;
    private String contractNumber;
    private String contractName;
    private String clientApi;
    private String contractualParties;
}
