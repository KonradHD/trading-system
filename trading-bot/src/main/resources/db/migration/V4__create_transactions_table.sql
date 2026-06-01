CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    wallet_id BIGINT NOT NULL,
    symbol VARCHAR(20) NOT NULL,
    order_id BIGINT NOT NULL,
    action VARCHAR(20) NOT NULL CHECK(action in ('BUY', 'SELL')),
    status VARCHAR(20) NOT NULL,
    timestamp BIGINT NOT NULL,
    quantity DOUBLE PRECISION NOT NULL,
    price_qty NUMERIC(24, 8) NOT NULL,
    type VARCHAR(20) NOT NULL,
    CONSTRAINT fk_transactions_wallet FOREIGN KEY(wallet_id) REFERENCES wallets(id) ON DELETE CASCADE
);
CREATE INDEX idx_transactions_symbol ON transactions(symbol);
CREATE INDEX idx_transactions_wallet ON transactions(wallet_id);