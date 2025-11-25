CREATE TABLE movie (
    id uuid PRIMARY KEY REFERENCES show(id) ON DELETE CASCADE,
    release_date date NOT NULL
);