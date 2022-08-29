CREATE TABLE IF NOT EXISTS team (
	team_id VARCHAR(40),
	name VARCHAR(60) NOT NULL,
	department VARCHAR(35),
	team_lead VARCHAR(40), -- foreign key for user
	active boolean NOT NULL DEFAULT '0',
	CONSTRAINT pk_team PRIMARY KEY(team_id),
    CONSTRAINT uk_team UNIQUE (name,department)
);