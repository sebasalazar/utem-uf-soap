BEGIN TRANSACTION;

DROP TABLE IF EXISTS users CASCADE; -- 16 kb
CREATE TABLE users (
    pk bigserial NOT NULL, -- 8bytes
    username varchar(255) NOT NULL, -- 16320 bytes
    password varchar(255) NOT NULL, -- 16320 bytes
    active boolean NOT NULL DEFAULT '0', -- 2 bytes
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX users_username_uidx ON users(LOWER(TRIM(both FROM username)));
INSERT INTO users (username,password,active) VALUES ('seba','fd43e2d7-255e-4d9b-a58c-2b15c3b7931a','1');


DROP TABLE IF EXISTS ufs CASCADE;
CREATE TABLE ufs (
    pk bigserial NOT NULL,
    uf_date date NOT NULL,
    uf_value numeric(999,2) NOT NULL,
    PRIMARY KEY (pk)
);
CREATE UNIQUE INDEX ufs_ufdate_uidx ON ufs(uf_date);


DROP TABLE IF EXISTS access CASCADE;
CREATE TABLE access (
    pk bigserial NOT NULL,
    user_fk bigint NOT NULL,
    uf_fk bigint NOT NULL,
    created timestamp NOT NULL DEFAULT NOW(),
    FOREIGN KEY (user_fk) REFERENCES users(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (uf_fk) REFERENCES ufs(pk) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (user_fk, uf_fk),
    PRIMARY KEY (pk)
);

COMMIT;
