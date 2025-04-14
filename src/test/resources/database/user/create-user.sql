DELETE FROM users;
DELETE FROM roles;

INSERT INTO users(id, username, password, email, first_name, last_name)
VALUES (1, 'user', '123', 'user@gmail.com', 'user', 'user');

INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
