CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    password VARCHAR NOT NULL
);

COMMENT ON TABLE "user" IS 'Пользователь';
COMMENT ON COLUMN "user".id IS 'Идентификатор пользователя';
COMMENT ON COLUMN "user".username IS 'Имя пользователя';
COMMENT ON COLUMN "user".password IS 'Пароль пользоватея';