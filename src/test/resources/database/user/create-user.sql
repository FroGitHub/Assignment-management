DELETE FROM users;
DELETE FROM roles;

INSERT INTO users(id, username, password, email, first_name, last_name)
VALUES (1, 'user', '$2a$10$tsXTqLug0brd6r.1.K8T2uGvoNSOK5RzeA6Yq6lZi/32wTfA6YIwe', 'user@gmail.com', 'user', 'user');

INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');
INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
