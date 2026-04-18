CREATE TABLE IF NOT EXISTS commissions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    transaction_id BIGINT NOT NULL,
    commission_value NUMERIC(15, 4) NOT NULL,
    commission_asset VARCHAR(20) NOT NULL,
    CONSTRAINT fk_transaction FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE
);
CREATE INDEX idx_trans_id_commission ON commissions(transaction_id);