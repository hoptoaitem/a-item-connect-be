CREATE TABLE IF NOT EXISTS zip (
    zip VARCHAR(128) PRIMARY KEY,
    city VARCHAR(128) NOT NULL,
    state VARCHAR(24) NOT NULL,
    lon VARCHAR(100),
    lat VARCHAR(100),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)