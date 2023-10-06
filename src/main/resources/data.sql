-- Примеры данных для сущности User
INSERT INTO users (username, password, full_name, email) VALUES
    ('user1', 'hashed_password1', 'Пользователь 1', 'user1@example.com'),
    ('user2', 'hashed_password2', 'Пользователь 2', 'user2@example.com'),
    ('user3', 'hashed_password3', 'Пользователь 3', 'user3@example.com'),
    ('user4', 'hashed_password4', 'Пользователь 4', 'user4@example.com'),
    ('user5', 'hashed_password5', 'Пользователь 5', 'user5@example.com'),
    ('user6', 'hashed_password6', 'Пользователь 6', 'user6@example.com'),
    ('user7', 'hashed_password7', 'Пользователь 7', 'user7@example.com'),
    ('user8', 'hashed_password8', 'Пользователь 8', 'user8@example.com'),
    ('user9', 'hashed_password9', 'Пользователь 9', 'user9@example.com'),
    ('user10', 'hashed_password10', 'Пользователь 10', 'user10@example.com'),
    ('user11', 'hashed_password11', 'Пользователь 11', 'user11@example.com'),
    ('user12', 'hashed_password12', 'Пользователь 12', 'user12@example.com'),
    ('user13', 'hashed_password13', 'Пользователь 13', 'user13@example.com'),
    ('user14', 'hashed_password14', 'Пользователь 14', 'user14@example.com'),
    ('user15', 'hashed_password15', 'Пользователь 15', 'user15@example.com');

-- Примеры данных для сущности Message
INSERT INTO messages (sender_id, receiver_id, content, timestamp) VALUES
    (1, 2, 'Привет, пользователь 2!', NOW()),
    (2, 1, 'Привет, пользователь 1!', NOW()),
    (3, 1, 'Привет, пользователь 1!', NOW()),
    (4, 2, 'Привет, пользователь 2!', NOW()),
    (5, 3, 'Привет, пользователь 3!', NOW()),
    (2, 3, 'Привет, пользователь 3!', NOW()),
    (1, 4, 'Привет, пользователь 4!', NOW()),
    (5, 4, 'Привет, пользователь 4!', NOW()),
    (3, 5, 'Привет, пользователь 5!', NOW()),
    (2, 5, 'Привет, пользователь 5!', NOW()),
    (4, 3, 'Привет, пользователь 3!', NOW()),
    (1, 6, 'Привет, пользователь 6!', NOW()),
    (6, 7, 'Привет, пользователь 7!', NOW()),
    (7, 8, 'Привет, пользователь 8!', NOW());

-- Примеры данных для сущности Post
INSERT INTO posts (user_id, content, timestamp) VALUES
    (1, 'Первый пост пользователя 1', NOW()),
    (2, 'Первый пост пользователя 2', NOW()),
    (1, 'Второй пост пользователя 1', NOW()),
    (3, 'Первый пост пользователя 3', NOW()),
    (4, 'Первый пост пользователя 4', NOW()),
    (2, 'Второй пост пользователя 2', NOW()),
    (5, 'Первый пост пользователя 5', NOW()),
    (3, 'Второй пост пользователя 3', NOW()),
    (1, 'Третий пост пользователя 1', NOW()),
    (4, 'Второй пост пользователя 4', NOW()),
    (5, 'Второй пост пользователя 5', NOW()),
    (2, 'Третий пост пользователя 2', NOW()),
    (1, 'Четвертый пост пользователя 1', NOW()),
    (4, 'Третий пост пользователя 4', NOW());
