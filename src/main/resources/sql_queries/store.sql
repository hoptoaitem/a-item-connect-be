CREATE TABLE IF NOT EXISTS store (
    id VARCHAR(128) PRIMARY KEY,
    address_id VARCHAR(128),
    retailer_user_id VARCHAR(128),
    phone_no VARCHAR(128),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
