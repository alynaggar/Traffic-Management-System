USE tms;

CREATE TABLE role (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
    PRIMARY KEY(id)
);

CREATE TABLE user (
	id BIGINT NOT NULL AUTO_INCREMENT,
	username VARCHAR(50) UNIQUE,
	password VARCHAR(60) NOT NULL,
    role_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(role_id) REFERENCES role(id)
);

CREATE TABLE privilege (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100),
    description VARCHAR(500),
    PRIMARY KEY(id)
);

CREATE TABLE role_privilege (
	role_id BIGINT NOT NULL,
	privilege_id BIGINT NOT NULL,
    PRIMARY KEY(role_id, privilege_id),
    FOREIGN KEY(role_id) REFERENCES role(id),
    FOREIGN KEY(privilege_id) REFERENCES privilege(id)
);

CREATE TABLE location (
	id BIGINT NOT NULL AUTO_INCREMENT,
	name VARCHAR(100),
    status VARCHAR(100),
    latitude DOUBLE,
    longitude DOUBLE,
    PRIMARY KEY(id)
);

CREATE TABLE traffic_light (
	id BIGINT NOT NULL AUTO_INCREMENT,
	status VARCHAR(100),
    location_id BIGINT,
    PRIMARY KEY(id),
    FOREIGN KEY(location_id) REFERENCES location(id)
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