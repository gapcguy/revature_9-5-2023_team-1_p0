-- Drop tables with CASCADE
DROP TABLE IF EXISTS transaction;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS treasurebox;
DROP TABLE IF EXISTS toy;

-- Create the account table
CREATE TABLE account (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    coin_balance INT DEFAULT 50 NOT NULL
);

-- Create the toy table
CREATE TABLE toy (
    toy_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    quantity INT NOT NULL,
    image VARCHAR(255) NOT NULL,
    cost INT NOT NULL DEFAULT 50
);

-- Insert data into the account table
INSERT INTO account (username, password)
VALUES
    ('user1', 'dallas'),
    ('user2', 'reston'),
    ('user3', 'morgantown'),
    ('user4', 'dallas'),
    ('user5', 'dallas'),
    ('user6', 'tampa');

-- Insert data into the toy table
INSERT INTO toy (name, quantity, image)
VALUES
    ('barbie', 25, 'https://revature.michaelwarner.info/img/barbie.png'),
    ('spongebob', 25, 'https://revature.michaelwarner.info/img/spongebob.png'),
    ('fidget spinner', 15, 'https://revature.michaelwarner.info/img/fidget-spinner.png'),
    ('gamecube', 10, 'https://revature.michaelwarner.info/img/GameCube-Set.png');

-- Create the transaction table
CREATE TABLE transaction (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    account_id_fk INT,
    toy_name VARCHAR(255) NOT NULL,
    toy_id_fk INT,
    FOREIGN KEY (account_id_fk) REFERENCES account(account_id) ON DELETE CASCADE,
    FOREIGN KEY (toy_id_fk) REFERENCES toy(toy_id) ON DELETE CASCADE
);

-- Insert data into the transaction table
INSERT INTO transaction (account_id_fk, toy_name, toy_id_fk)
VALUES
    (1, 'barbie', 1),
    (2, 'spongebob', 2),
    (3, 'fidget spinner', 3);
