CREATE TABLE "user" (
    id serial PRIMARY KEY,
    username text NOT NULL UNIQUE,
    password_hash char(60) NOT NULL
);