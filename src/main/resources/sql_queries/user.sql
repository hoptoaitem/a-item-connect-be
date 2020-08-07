CREATE TABLE IF NOT EXISTS user (
    id VARCHAR(128) PRIMARY KEY,
    username VARCHAR(128) NOT NULL UNIQUE,
    first_name VARCHAR(128),
    last_name VARCHAR(128),
    email VARCHAR(128),
    phone VARCHAR(128),
    status VARCHAR(128),
    pass VARCHAR(64) NOT NULL,
    iv VARCHAR(24) NOT NULL,
    salt VARCHAR(44) NOT NULL,
    profile_type VARCHAR(44) NOT NULL,
    address_id VARCHAR(128),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
  )
