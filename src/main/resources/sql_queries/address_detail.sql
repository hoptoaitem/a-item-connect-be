CREATE TABLE IF NOT EXISTS address_detail (
    id VARCHAR(128) PRIMARY KEY,
    first_name VARCHAR(128),
    last_name VARCHAR(128),
    phone VARCHAR(128),
    email VARCHAR(128),
    street_address_1 VARCHAR(128),
    street_address VARCHAR(128) NOT NULL,
    city VARCHAR(128) NOT NULL,
    state VARCHAR(24) NOT NULL,
    zip VARCHAR(64) NOT NULL,
    lon DECIMAL,
    lat DECIMAL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
