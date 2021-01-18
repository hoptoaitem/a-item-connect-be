CREATE TABLE IF NOT EXISTS shop_order (
    id VARCHAR(128) PRIMARY KEY,
    order_external_ref_id VARCHAR(128) NOT NULL,
    store_id VARCHAR(128) NOT NULL,
    user_id VARCHAR(128) ,
    driver_id VARCHAR(128),
    history VARCHAR(256),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(128) NOT NULL
)
