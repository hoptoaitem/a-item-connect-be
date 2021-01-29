CREATE TABLE IF NOT EXISTS transaction (
    id VARCHAR(128) PRIMARY KEY,
    user_id VARCHAR(128) NOT NULL,
    order_id VARCHAR(128) NOT NULL,
    item_id VARCHAR(128) NOT NULL,
    billing_address VARCHAR(128),
    deliver_address VARCHAR(128),
    quantity INTEGER NOT NULL,
    price VARCHAR(100),
    note VARCHAR(128),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
