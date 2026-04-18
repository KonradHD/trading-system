CREATE TABLE IF NOT EXISTS profiles (
    user_id BIGINT PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE,
    name VARCHAR(50) NOT NULL,
    surname VARCHAR(50) NOT NULL,
    gender VARCHAR(10) NOT NULL,
    date_of_birth DATE NOT NULL CHECK(date_of_birth < CURRENT_DATE),
    city VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone CHAR(9) NOT NULL CHECK(phone ~ '^[0-9]{9}$'),
    pesel CHAR(11) NOT NULL CHECK(pesel ~ '^[0-9]{11}$'),
    CONSTRAINT fk_user_id_profiles FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT check_gender CHECK(gender in ('male', 'female', 'other'))
);
CREATE INDEX idx_email ON profiles(email);