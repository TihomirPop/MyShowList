CREATE TABLE show (
    id uuid PRIMARY KEY DEFAULT uuidv7(),
    title text NOT NULL,
    description text NOT NULL
);