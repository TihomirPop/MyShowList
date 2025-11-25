CREATE TABLE tv_series (
    id uuid PRIMARY KEY REFERENCES show(id) ON DELETE CASCADE,
    episode_count integer NOT NULL,
    started_date date NOT NULL,
    ended_date date NOT NULL
);