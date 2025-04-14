DELETE FROM projects;
INSERT INTO projects (id, name, description, start_date, end_date, status)
VALUES (
    1,
    'Project Alpha',
    'This is a sample project description.',
    '2025-04-01',
    '2025-06-30',
    'IN_PROGRESS'
);
