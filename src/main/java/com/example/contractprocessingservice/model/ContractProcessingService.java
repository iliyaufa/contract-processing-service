package com.example.contractprocessingservice.model;

import com.example.contractprocessingservice.repository.ContractDao;
import org.springframework.stereotype.Service;

@Service
public class ContractProcessingService {

    private ContractDao contractDao;

    public ContractProcessingService(ContractDao contractDao) {
        this.contractDao = contractDao;
    }

    public ContractStatus contractHandle(ContractDto contractDto) {
        ContractStatus status = new ContractStatus();
        status.setId(contractDto.getId());
        status.setStatus(ContractStatus.Status.ERROR);
        if (contractDao.getContractById(contractDto.getId()).isPresent()) {
            status.setErrorCode(1);
            return status;
        } else if (contractDao.getContractByContractNumber(contractDto.getContractNumber()).isPresent()) {
            status.setErrorCode(2);
            return status;
        } status.setStatus(ContractStatus.Status.CREATED);
        status.setDateCreate(contractDao.create(contractDto));
        return status;
    }
}
