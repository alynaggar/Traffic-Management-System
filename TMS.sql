USE tms;

CREATE TABLE role (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
    PRIMARY KEY(id)
);

CREATE TABLE user (
	id BIGINT NOT NULL AUTO_INCREMENT,
	username VARCHAR(50) UNIQUE,
    name VARCHAR(200),
	password VARCHAR(60) NOT NULL,
    email VARCHAR(200) UNIQUE,
    otp VARCHAR(4),
    role_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(role_id) REFERENCES role(id)
);

CREATE TABLE location (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100),
    status VARCHAR(100),
    latitude DOUBLE,
    longitude DOUBLE,
    PRIMARY KEY(id)
);

CREATE TABLE camera (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100),
    url VARCHAR(500),
    status VARCHAR(50),
    location_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(location_id) REFERENCES location(id)
);
