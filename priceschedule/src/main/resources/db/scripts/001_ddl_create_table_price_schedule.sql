CREATE TABLE price_schedule (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    created TIMESTAMP NOT NULL,
    effective BOOLEAN NOT NULL
);

COMMENT ON TABLE price_schedule IS 'Прейскурант';
COMMENT ON COLUMN price_schedule.id IS 'Идентификатор прейскурранта';
COMMENT ON COLUMN price_schedule.name IS 'Уникальное имя прейскуранта';
COMMENT ON COLUMN price_schedule.created IS 'Дата и время создания прейскуранта';
COMMENT ON COLUMN price_schedule.effective IS 'Признак действительности прейскуранта';