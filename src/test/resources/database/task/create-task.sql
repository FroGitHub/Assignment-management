DELETE FROM tasks;

INSERT INTO tasks(id, name, description, priority, status, due_date, project_id, assignee_id)
VALUES (1, 'task', 'descrpt', 'LOW', 'IN_PROGRESS', '2025-05-01', 1, 1);
