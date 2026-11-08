ALTER TABLE app_users
    ADD COLUMN must_change_password BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE app_users
SET must_change_password = TRUE
WHERE role = 'STUDENT';
