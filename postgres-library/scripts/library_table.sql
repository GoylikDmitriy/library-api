CREATE TABLE library (
    book_id BIGSERIAL PRIMARY KEY,
    borrowed_dt TIMESTAMP,
    return_dt TIMESTAMP
);