DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE SEQUENCE hibernate_sequence
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 146
CACHE 1;

DROP TABLE IF EXISTS oldera_player_level_history;
DROP TABLE IF EXISTS oldera_player_death;
DROP TABLE IF EXISTS oldera_player;
DROP TABLE IF EXISTS thronia_player_death;
DROP TABLE IF EXISTS thronia_player;
DROP TABLE IF EXISTS tibia_player;
DROP TABLE IF EXISTS tibia_server;

DROP TABLE IF EXISTS Test;
/*DROP TABLE IF EXISTS UserConnection;*/
DROP TABLE IF EXISTS persistent_logins;
DROP TABLE IF EXISTS password_reset_token;
DROP TABLE IF EXISTS verification_token;
DROP TABLE IF EXISTS accounts_roles;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS account;


CREATE TABLE account
(
  id bigint NOT NULL,
  email character varying(255) NOT NULL,
  password character varying(255) NOT NULL,
  enabled boolean NOT NULL DEFAULT FALSE,
  CONSTRAINT account_pkey PRIMARY KEY (id)
);

CREATE TABLE role
(
  id bigint NOT NULL,
  role character varying(50) NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (id)
);

INSERT INTO account(id, email, password, enabled) VALUES (1, 'jose@jo.se', '$2a$10$kMVhUDUeDSdZOeCOboXzXOwy9f4VXvIp2yE0OEHIWA.BTlaX6.vpq', TRUE);
INSERT INTO account(id, email, password, enabled) VALUES (2, 'adrianq92@hotmail.com', '$2a$10$AK1rKs1jY0W0qjACmoDioO7gzCzJIxAfXDBgOi0gfyYaf.adw8m7y', TRUE);

INSERT INTO role(id,role) VALUES (1, 'ROLE_USER');
INSERT INTO role(id,role) VALUES (2, 'ROLE_ADMIN');

CREATE TABLE accounts_roles
(
  account_id bigint NOT NULL,
  role_id bigint NOT NULL,
  CONSTRAINT usersroles_accid_fkey FOREIGN KEY (account_id) REFERENCES account (id),
  CONSTRAINT usersroles_roleid_fkey FOREIGN KEY (role_id) REFERENCES role (id)
);

INSERT INTO accounts_roles(account_id, role_id) VALUES (1, 1);
INSERT INTO accounts_roles(account_id, role_id) VALUES (1, 2);
INSERT INTO accounts_roles(account_id, role_id) VALUES (2, 1);
INSERT INTO accounts_roles(account_id, role_id) VALUES (2, 2);

/*Email Verification Token*/
CREATE TABLE verification_token
(
  id bigint NOT NULL,
  token character varying(255) NOT NULL,
  expiry_date timestamp without time zone NOT NULL,
  account_id bigint NOT NULL,
  CONSTRAINT verification_token_pkey PRIMARY KEY (id),
  CONSTRAINT verification_token_fkey FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE password_reset_token
(
  id bigint NOT NULL,
  token character varying(255) NOT NULL,
  account_id bigint NOT NULL,
  expiry_date timestamp without time zone NOT NULL,
  CONSTRAINT password_reset_token_pkey PRIMARY KEY (id),
  CONSTRAINT password_reset_token_fkey FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE persistent_logins
(
  username VARCHAR(64) NOT NULL,
  series VARCHAR(64) NOT NULL,
  token VARCHAR(64) NOT NULL,
  last_used TIMESTAMP NOT NULL,
  PRIMARY KEY (series)
);

/*Social User connection*/
/*create table UserConnection (userId varchar(255) not null,
    providerId varchar(255) not null,
    providerUserId varchar(255),
    rank int not null,
    displayName varchar(255),
    profileUrl varchar(512),
    imageUrl varchar(512),
    accessToken varchar(255) not null,
    secret varchar(255),
    refreshToken varchar(255),
    expireTime bigint,
    primary key (userId, providerId, providerUserId));
create unique index UserConnectionRank on UserConnection(userId, providerId, rank);*/

create table Test (
  id bigint NOT NULL,
  account_id bigint NOT NULL,
  test_word varchar(100),
  CONSTRAINT test_pkey PRIMARY KEY (id),
  CONSTRAINT test_fkey FOREIGN KEY (account_id) REFERENCES account(id)
);

CREATE TABLE tibia_server(
  name VARCHAR(100) NOT NULL,
  update_interval_ms INTEGER NOT NULL,
  /*url VARCHAR(120),*/
  /*whoIsOnlineUrl VARCHAR(240),*/
  is_online boolean NOT NULL DEFAULT FALSE,
  /*lastUpdateTs TIMESTAMP,*/
  CONSTRAINT tibia_server_pkey PRIMARY KEY (name)
);

CREATE TABLE tibia_player(
  id BIGINT NOT NULL,
  server_name VARCHAR(100) NOT NULL,
  name VARCHAR(90) NOT NULL,
  level INTEGER NOT NULL,
  vocation VARCHAR(50) NOT NULL,
  is_online boolean NOT NULL DEFAULT FALSE,
  CONSTRAINT tibia_player_pkey PRIMARY KEY (id),
  CONSTRAINT tibia_player_fkey FOREIGN KEY (server_name) REFERENCES tibia_server(name)

);

CREATE TABLE thronia_player(
  id BIGINT NOT NULL,
  CONSTRAINT thronia_player_pkey PRIMARY KEY (id)
);

CREATE TABLE thronia_player_death(
  id BIGINT NOT NULL,
  death_player_id BIGINT NOT NULL,
  first_killer_id BIGINT,
  second_killer_id BIGINT,
  has_two_killers BOOLEAN NOT NULL,
  first_killer_is_creature BOOLEAN NOT NULL,
  second_killer_is_creature BOOLEAN NOT NULL,
  first_killer_name VARCHAR(100) NOT NULL,
  second_killer_name VARCHAR(100),
  ts_death timestamp without time zone NOT NULL,
  CONSTRAINT thronia_player_death_pkey PRIMARY KEY(id),
  CONSTRAINT death_player_id_fkey FOREIGN KEY (death_player_id) REFERENCES thronia_player(id),
  CONSTRAINT first_killer_fkey FOREIGN KEY (first_killer_id) REFERENCES thronia_player(id),
  CONSTRAINT second_killer_fkey FOREIGN KEY (second_killer_id) REFERENCES thronia_player(id)
);

CREATE TABLE oldera_player(
  id BIGINT NOT NULL,
  CONSTRAINT oldera_player_pkey PRIMARY KEY (id),
  CONSTRAINT oldera_player_fkey FOREIGN KEY (id) REFERENCES tibia_player(id)
);

CREATE TABLE oldera_player_death(
  id BIGINT NOT NULL,
  death_player_id BIGINT NOT NULL,
  first_killer_id BIGINT,
  second_killer_id BIGINT,
  has_two_killers BOOLEAN NOT NULL,
  first_killer_is_creature BOOLEAN NOT NULL,
  second_killer_is_creature BOOLEAN NOT NULL,
  first_killer_name VARCHAR(100) NOT NULL,
  second_killer_name VARCHAR(100),
  ts_death TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT oldera_player_death_pkey PRIMARY KEY(id),
  CONSTRAINT death_player_id_fkey FOREIGN KEY (death_player_id) REFERENCES oldera_player(id),
  CONSTRAINT first_killer_fkey FOREIGN KEY (first_killer_id) REFERENCES oldera_player(id),
  CONSTRAINT second_killer_fkey FOREIGN KEY (second_killer_id) REFERENCES oldera_player(id)
);

CREATE TABLE oldera_player_level_history(
  id BIGINT NOT NULL,
  oldera_player_id BIGINT NOT NULL,
  level INTEGER NOT NULL,
  ts TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT oldera_player_level_history_pkey PRIMARY KEY (id),
  CONSTRAINT oldera_player_level_history_fkey FOREIGN KEY (oldera_player_id) REFERENCES oldera_player(id)
);

INSERT INTO tibia_server(name,update_interval_ms,is_online) VALUES ('Thronia', 30000, false);
INSERT INTO tibia_server(name,update_interval_ms,is_online) VALUES ('Oldera', 30000, false);

/*

CREATE TABLE tibia_player(
  id serial NOT NULL,
  server_id BIGINT NOT NULL,
  name VARCHAR(100) NOT NULL,
  level BIGINT NOT NULL,
  vocation VARCHAR(20) NOT NULL,
  CONSTRAINT tibiaplayer_pkey PRIMARY KEY (id),
  CONSTRAINT tibiaplayer_fkey FOREIGN KEY (server_id) REFERENCES tibia_server(id).o s

);

CREATE TABLE tibia_player_log(
  id serial NOT NULL,
  player_id BIGINT NOT NULL,
  ts TIMESTAMP NOT NULL,
  log VARCHAR(512) NOT NULL,
  CONSTRAINT tibiaplayerlog_pkey PRIMARY KEY (id),
  CONSTRAINT tibiaplayerlog_fkey FOREIGN KEY (player_id) REFERENCES tibia_player(id)
);
*/
