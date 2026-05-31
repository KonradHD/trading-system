CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    binance_api_key VARCHAR(255),
    binance_secret_key VARCHAR(255),
    CONSTRAINT check_role CHECK (role in ('ADMIN', 'USER'))
);
CREATE INDEX idx_login ON users(login);