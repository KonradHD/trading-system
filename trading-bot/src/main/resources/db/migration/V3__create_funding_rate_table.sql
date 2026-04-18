CREATE TABLE IF NOT EXISTS funding_rate (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    funding_rate DOUBLE PRECISION NOT NULL,
    timestamp BIGINT NOT NULL,
    price NUMERIC(15, 4) NOT NULL
);
CREATE INDEX idx_funding_rate_symbol ON funding_rate(symbol);