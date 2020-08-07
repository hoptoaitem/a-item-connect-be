CREATE TABLE IF NOT EXISTS address (
    id VARCHAR(128) PRIMARY KEY,
    address_name VARCHAR(128),
    street_address_1 VARCHAR(128),
    street_address VARCHAR(128) NOT NULL,
    city VARCHAR(128) NOT NULL,
    zip VARCHAR(64) NOT NULL,
    state VARCHAR(24) NOT NULL,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
