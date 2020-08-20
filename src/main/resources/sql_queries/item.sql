CREATE TABLE IF NOT EXISTS item (
  id VARCHAR(128) PRIMARY KEY,
  name VARCHAR(128) NOT NULL,
  type VARCHAR(128) NOT NULL,
  quantity INTEGER NOT NULL,
  website VARCHAR(24),
  description VARCHAR(400),
  short_description VARCHAR(200),
  price VARCHAR(100),
  status VARCHAR(100),
  sku VARCHAR(100),
  weight VARCHAR(100),
  visibility VARCHAR(100),
  store_id VARCHAR(100),
  created_by VARCHAR(100),
  modified_by VARCHAR(100),
  picture_id VARCHAR(100),
  created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
  modified_at DATETIME DEFAULT CURRENT_TIMESTAMP
)

