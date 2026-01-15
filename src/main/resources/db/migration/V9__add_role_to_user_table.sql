ALTER TABLE "user" ADD COLUMN role VARCHAR(10) NOT NULL DEFAULT 'USER';

CREATE INDEX idx_user_role ON "user"(role);

ALTER TABLE "user" ADD CONSTRAINT chk_user_role CHECK (role IN ('USER', 'ADMIN'));
