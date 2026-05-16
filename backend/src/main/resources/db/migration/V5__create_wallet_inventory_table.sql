CREATE TABLE IF NOT EXISTS wallet_inventory (
    wallet_id BIGINT,
    stock_symbol VARCHAR(20),
    quantity NUMERIC(24, 8) NOT NULL CHECK(quantity >= 0),
    CONSTRAINT pk_wallet_inv PRIMARY KEY (wallet_id, stock_symbol),
    CONSTRAINT fk_wallet_id_inventory FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE
);