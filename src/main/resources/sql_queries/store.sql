CREATE TABLE IF NOT EXISTS store (
    id VARCHAR(128) PRIMARY KEY,
    address_id VARCHAR(128),
    phone_no VARCHAR(128),
    email VARCHAR(128),
    website VARCHAR(128),
    type INTEGER NOT NULL,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
