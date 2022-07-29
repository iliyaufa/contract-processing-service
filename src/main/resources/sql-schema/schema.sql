DROP TABLE IF EXISTS contract;
CREATE TABLE contract(
    id UUID PRIMARY KEY NOT NULL ,
    date_start VARCHAR(20),
    date_end VARCHAR(20),
    date_send TIMESTAMP,
    date_create TIMESTAMP DEFAULT NOW(),
    contract_number VARCHAR(30) NOT NULL UNIQUE ,
    contract_name VARCHAR(50),
    client_api VARCHAR(5),
    contractual_parties JSON
);
CREATE UNIQUE INDEX IF NOT EXISTS idx_contract_number ON contract (contract_number);