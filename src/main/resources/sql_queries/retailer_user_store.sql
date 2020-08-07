CREATE TABLE IF NOT EXISTS retailer_user_store (
    id VARCHAR(128) PRIMARY KEY,
    store_id VARCHAR(128) NOT NULL,
    user_id VARCHAR(128) NOT NULL,
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
  )
