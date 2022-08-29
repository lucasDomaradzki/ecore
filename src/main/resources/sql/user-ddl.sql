CREATE TABLE IF NOT EXISTS user (
	user_id VARCHAR(40),
	name VARCHAR(60) NOT NULL,
	date_issued DATETIME NOT NULL,
	date_update DATETIME,
	active boolean NOT NULL DEFAULT '0',
	team VARCHAR(40), -- foreign key for team
	role int(6),
	CONSTRAINT pk_user PRIMARY KEY(user_id),
	CONSTRAINT uk_user UNIQUE (name)
);