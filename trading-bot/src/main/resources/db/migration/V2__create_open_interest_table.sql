CREATE TABLE IF NOT EXISTS open_interest (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    open_interest DOUBLE PRECISION NOT NULL,
    timestamp BIGINT NOT NULL
);
CREATE INDEX idx_open_interest_symbol ON open_interest(symbol);