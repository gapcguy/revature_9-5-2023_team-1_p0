DROP TABLE IF EXISTS transaction CASCADE;
DROP TABLE IF EXISTS account CASCADE;
DROP TABLE IF EXISTS treasurebox CASCADE;
DROP TABLE IF EXISTS toy CASCADE;


CREATE TABLE account(
    account_id serial PRIMARY KEY,
    username text UNIQUE NOT NULL,
    password text NOT NULL,
    coin_balance int DEFAULT 50 NOT NULL);

CREATE TABLE toy(
    toy_id serial PRIMARY KEY,
    name text UNIQUE NOT NULL,
    quantity int NOT NULL,
    image text NOT NULL,
    cost int NOT NULL DEFAULT 50);

INSERT INTO account (username, password)
VALUES ('user1', 'dallas'),
       ('user2', 'reston'),
       ('user3', 'morgantown'),
       ('user4', 'dallas'),
       ('user5', 'dallas'),
       ('user6', 'tampa');

INSERT INTO toy (name, quantity, image)
VALUES ('barbie', 25, 'https://revature.michaelwarner.info/img/barbie.png'),
       ('spongebob', 25, 'https://revature.michaelwarner.info/img/spongebob.png'),
       ('fidget spinner', 15, 'https://revature.michaelwarner.info/img/fidget-spinner.png'),
       ('gamecube', 10, 'https://revature.michaelwarner.info/img/GameCube-Set.png');

create table transaction(
    transaction_id serial PRIMARY KEY,
    account_id_fk INT REFERENCES account(account_id),
    toy_name text NOT NULL,
    toy_id_fk INT REFERENCES toy(toy_id));


INSERT INTO transaction (account_id_fk,toy_name,toy_id_fk)
VALUES (1,'barbie',1),
       (2,'spongebob',2),
       (3,'fidget spinner',3);