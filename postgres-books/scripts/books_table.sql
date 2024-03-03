CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    isbn VARCHAR(17) UNIQUE,
    title VARCHAR,
    genre VARCHAR,
    description TEXT,
    author VARCHAR
);