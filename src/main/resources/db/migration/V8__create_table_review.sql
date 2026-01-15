CREATE TABLE review (
    user_id INTEGER NOT NULL,
    show_id UUID NOT NULL,
    review_text TEXT NOT NULL,
    PRIMARY KEY (user_id, show_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (show_id) REFERENCES show(id) ON DELETE CASCADE
);

CREATE INDEX idx_review_show_id ON review(show_id);
