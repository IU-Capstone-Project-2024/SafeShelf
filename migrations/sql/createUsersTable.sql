CREATE TABLE IF NOT EXISTS Users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    login    VARCHAR(255),
    password VARCHAR(255),
    name     VARCHAR(255),
    surname  VARCHAR(255),
    age      INT,
    sex      CHAR(1)
);