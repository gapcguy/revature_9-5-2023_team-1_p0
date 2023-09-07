DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS treasurebox;
DROP TABLE IF EXISTS toy;
DROP TABLE IF EXISTS transaction;

CREATE TABLE account(
    account_id serial PRIMARY KEY,
    username text UNIQUE NOT NULL,
    password text NOT NULL,
    coin_balance int DEFAULT 50 NOT NULL,
    coin_income int DEFAULT 50 NOT NULL,
    coin_outcome int DEFAULT 0 NOT NULL);

CREATE TABLE toy(
    toy_id serial PRIMARY KEY,
    name text UNIQUE NOT NULL,
    quantity int NOT NULL,
    cost int NOT NULL DEFAULT 50);

INSERT INTO account (username, password)
VALUES ('user1', 'dallas'),
       ('user2', 'reston'),
       ('user3', 'morgantown'),
       ('user4', 'dallas'),
       ('user5', 'dallas'),
       ('user6', 'tampa');

INSERT INTO toy (name, quantity)
VALUES ('barbie', 10),
       ('spongebob', 25),
       ('fidget spinner', '4'),
       ('gamecube', '100');

create table transaction(
    transaction_id serial PRIMARY KEY,
    account_id_fk INT REFERENCES account(account_id),
    toy_name text UNIQUE NOT NULL,
    toy_id_fk INT REFERENCES toy(toy_id));