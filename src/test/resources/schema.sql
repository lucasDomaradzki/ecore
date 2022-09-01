CREATE TABLE IF NOT EXISTS role (
	role_id INTEGER NOT NULL AUTO_INCREMENT,
	name VARCHAR(60) NOT NULL,
	PRIMARY KEY(role_id),
	UNIQUE(name)
);

-- Minimal roles required
INSERT INTO role (role_id, name)
VALUES (1, 'DEVELOPER');

INSERT INTO role (role_id, name)
VALUES (2, 'PRODUCT_OWNER');

INSERT INTO role (role_id, name)
VALUES (3, 'TESTER');

CREATE TABLE IF NOT EXISTS team (
	team_id VARCHAR(40),
	name VARCHAR(60) NOT NULL,
	department VARCHAR(35),
	team_lead VARCHAR(40), -- foreign key for user
	active boolean NOT NULL DEFAULT '0',
	CONSTRAINT pk_team PRIMARY KEY(team_id),
    CONSTRAINT uk_team UNIQUE (name,department)
);

CREATE TABLE IF NOT EXISTS user (
	user_id VARCHAR(40),
	name VARCHAR(60) NOT NULL,
	date_issued TIMESTAMP NOT NULL,
	date_update TIMESTAMP,
	active BOOLEAN NOT NULL DEFAULT '0',
	team VARCHAR(40), -- foreign key for team
	role INTEGER,
	PRIMARY KEY(user_id),
	UNIQUE (name)
);