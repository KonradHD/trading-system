CREATE TABLE IF NOT EXISTS candles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    symbol VARCHAR(20) NOT NULL,
    open_price NUMERIC(15, 4) NOT NULL,
    close_price NUMERIC(15, 4) NOT NULL,
    high_price NUMERIC(15, 4) NOT NULL CHECK(
        high_price >= open_price
        and high_price >= close_price
    ),
    low_price NUMERIC(15, 4) NOT NULL CHECK(
        low_price <= open_price
        and low_price <= close_price
    ),
    volume DOUBLE PRECISION NOT NULL,
    open_time BIGINT NOT NULL,
    close_time BIGINT NOT NULL CHECK(close_time >= open_time),
    is_closed BOOLEAN NOT NULL
);
CREATE INDEX idx_symbol_candles ON candles(symbol);