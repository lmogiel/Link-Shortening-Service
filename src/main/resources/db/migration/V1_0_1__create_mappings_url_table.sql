CREATE TABLE IF NOT EXISTS `mappings_url`(
	`id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`long_url` longtext NOT NULL,
	`shortened_code` varchar(10) NOT NULL,
	`click_count` bigint NOT NULL DEFAULT 0,
	`created_at` datetime(6) DEFAULT NULL,
	`created_by` bigint DEFAULT NULL,
  
	CONSTRAINT FK_users 
    FOREIGN KEY (CREATED_BY) 
    REFERENCES users(id)
	
);


