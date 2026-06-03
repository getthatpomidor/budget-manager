CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(15,2) NOT NULL,
    transactionType VARCHAR(20) NOT NULL,
    category VARCHAR(100) NOT NULL,
    description TEXT,
    transaction_date DATE NOT NULL,
    account_id BIGINT NOT NULL,

    CONSTRAINT fk_transactions_account
    FOREIGN KEY (account_id) REFERENCES accounts(id)

)