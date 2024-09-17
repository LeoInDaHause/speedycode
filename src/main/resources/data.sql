-- Default user
INSERT INTO users (email, password)
SELECT NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email IS NULL AND password IS NULL);

-- Default exercise
INSERT INTO exercise_status (user_id, easy_1, easy_2, easy_3, easy_4, medium_1, medium_2, medium_3, medium_4, hard_1, hard_2, hard_3, hard_4)
SELECT 1, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE
WHERE NOT EXISTS (SELECT 1 FROM exercise_status WHERE user_id = 1);