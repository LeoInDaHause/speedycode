-- Insert a default user if not exists
INSERT INTO users (email, password)
SELECT 'default@example.com', NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'default@example.com');

-- Insert default exercise status for the default user if not exists
INSERT INTO exercises_status (user_id, easy_1, easy_2, easy_3, easy_4, medium_1, medium_2, medium_3, medium_4, hard_1, hard_2, hard_3, hard_4)
SELECT id, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE
FROM users
WHERE email = 'default@example.com'
AND NOT EXISTS (SELECT 1 FROM exercises_status WHERE user_id = (SELECT id FROM users WHERE email = 'default@example.com'));