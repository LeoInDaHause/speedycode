MERGE INTO person AS p
USING (SELECT 0 AS id) AS src
ON (p.id = src.id)
WHEN NOT MATCHED THEN
INSERT (id, email, password, exercise_status_id) VALUES (0, NULL, NULL, NULL);