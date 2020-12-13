CREATE TABLE IF NOT EXISTS picture (
    id VARCHAR(128) PRIMARY KEY,
    path VARCHAR(128) NOT NULL,
    url VARCHAR(1000),
    original_file_name VARCHAR(128),
    created_by VARCHAR(100),
    modified_by VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)
