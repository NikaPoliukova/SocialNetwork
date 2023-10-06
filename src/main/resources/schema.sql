
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    sender_id BIGINT NOT NULL,
    receiver_id BIGINT NOT NULL,
    content TEXT,
    timestamp TIMESTAMP
);

в
CREATE TABLE posts (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    content TEXT,
    timestamp TIMESTAMP
);

CREATE TABLE user_following (
    user_id BIGINT UNSIGNED NOT NULL,
    following_id BIGINT UNSIGNED NOT NULL,
    PRIMARY KEY (user_id, following_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (following_id) REFERENCES users(id)
);

ALTER TABLE messages ADD FOREIGN KEY (sender_id) REFERENCES users(id);
ALTER TABLE messages ADD FOREIGN KEY (receiver_id) REFERENCES users(id);
ALTER TABLE posts ADD FOREIGN KEY (user_id) REFERENCES users(id);
