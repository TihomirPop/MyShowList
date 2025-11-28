CREATE TABLE user_show (
    user_id int NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    show_id uuid NOT NULL REFERENCES show(id) ON DELETE CASCADE,
    progress int NOT NULL,
    status smallint NOT NULL,
    score smallint,
    PRIMARY KEY (user_id, show_id)
);