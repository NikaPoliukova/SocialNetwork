-- Вставка данных в таблицу users
INSERT INTO users (username, password, email, created_at)
VALUES
    ('user1', 'password1', 'user1@example.com', NOW()),
    ('user2', 'password2', 'user2@example.com', NOW()),
    ('user3', 'password3', 'user3@example.com', NOW()),
    ('user4', 'password4', 'user4@example.com', NOW()),
    ('user5', 'password5', 'user5@example.com', NOW()),
    ('user6', 'password6', 'user6@example.com', NOW()),
    ('user7', 'password7', 'user7@example.com', NOW()),
    ('user8', 'password8', 'user8@example.com', NOW()),
    ('user9', 'password9', 'user9@example.com', NOW()),
    ('user10', 'password10', 'user10@example.com', NOW());

-- Вставка данных в таблицу posts
INSERT INTO posts (user_id, content, timestamp)
VALUES
    (1, 'Пост пользователя 1', NOW()),
    (2, 'Пост пользователя 2', NOW()),
    (3, 'Пост пользователя 3', NOW()),
    (4, 'Пост пользователя 4', NOW()),
    (5, 'Пост пользователя 5', NOW()),
    (6, 'Пост пользователя 6', NOW()),
    (7, 'Пост пользователя 7', NOW()),
    (8, 'Пост пользователя 8', NOW()),
    (9, 'Пост пользователя 9', NOW()),
    (10, 'Пост пользователя 10', NOW());


-- Вставка данных в таблицу messages
INSERT INTO messages (sender_id, receiver_id, content, timestamp)
VALUES
    (1, 2, 'Привет, пользователь 2!', NOW()),
    (2, 1, 'Привет, пользователь 1!', NOW()),
    (3, 4, 'Как дела, пользователь 4?', NOW()),
    (4, 3, 'Всё хорошо, пользователь 3!', NOW()),
    (5, 6, 'Здравствуйте, пользователь 6!', NOW()),
    (6, 5, 'Добрый день, пользователь 5!', NOW()),
    (7, 8, 'Приветствую, пользователь 8!', NOW()),
    (8, 7, 'Здесь пользователь 7!', NOW()),
    (9, 10, 'Как прошёл ваш день, пользователь 10?', NOW()),
    (10, 9, 'День был успешный, пользователь 9!', NOW());



INSERT INTO user_following (user_id)
VALUES
    (1),
    (1),
    (2),
    (2),
    (3),
    (3),
    (4),
    (4),
    (5),
    (5);
