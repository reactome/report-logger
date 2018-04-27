CREATE DATABASE IF NOT EXISTS report;

DROP TABLE if EXISTS report;
DROP TABLE if EXISTS useragenttype;

CREATE TABLE report (
    id bigint NOT NULL auto_increment,
    userAgent VARCHAR(255),
    created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ip VARCHAR(255),
    releaseNumber INTEGER,
    term VARCHAR(255) NOT NULL,
    type_id bigint,
    PRIMARY KEY (id)
) engine=InnoDB;

CREATE TABLE useragenttype (
    id bigint NOT NULL auto_increment,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) engine=InnoDB;

ALTER TABLE useragenttype ADD CONSTRAINT UK_UAT_NAME UNIQUE (name);

ALTER TABLE report ADD CONSTRAINT FK_REP_UAT FOREIGN KEY (type_id) REFERENCES useragenttype (id);