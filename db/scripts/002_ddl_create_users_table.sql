CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    TEXT unique not null,
    name     TEXT        not null,
    password TEXT        not null
);