CREATE TABLE user_account (
    id uuid PRIMARY KEY,
    user_name varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    icon varchar(65536),
    create_time timestamp,
    modify_time timestamp
);

CREATE INDEX users_user_name_index ON user_account (user_name);