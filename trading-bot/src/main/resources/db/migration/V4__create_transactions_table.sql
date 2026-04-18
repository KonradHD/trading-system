CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    order_id BIGINT NOT NULL,
    action VARCHAR(20) NOT NULL CHECK(action in ('BUY', 'SELL')),
    status VARCHAR(20) NOT NULL,
    timestamp BIGINT NOT NULL,
    quantity DOUBLE PRECISION NOT NULL,
    price_qty NUMERIC(15, 4) NOT NULL,
    type VARCHAR(20) NOT NULL
);
CREATE INDEX idx_transations_symbol ON transactions(symbol);