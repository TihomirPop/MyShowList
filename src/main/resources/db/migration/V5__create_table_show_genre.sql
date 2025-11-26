CREATE TABLE show_genre (
    show_id uuid NOT NULL REFERENCES show(id) ON DELETE CASCADE,
    genre_id smallint NOT NULL REFERENCES genre(id) ON DELETE CASCADE,
    PRIMARY KEY (show_id, genre_id)
);