CREATE TABLE price_schedule (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    created TIMESTAMP NOT NULL,
    effective BOOLEAN NOT NULL
);

CREATE UNIQUE INDEX u_price_schedule_allow_only_one_effective_to_be_true
    ON price_schedule(effective) WHERE (effective = TRUE);

COMMENT ON TABLE price_schedule IS 'Прейскурант';
COMMENT ON COLUMN price_schedule.id IS 'Идентификатор прейскурранта';
COMMENT ON COLUMN price_schedule.name IS 'Уникальное имя прейскуранта';
COMMENT ON COLUMN price_schedule.created IS 'Дата и время создания прейскуранта';
COMMENT ON COLUMN price_schedule.effective IS 'Признак действительности прейскуранта';

COMMENT ON INDEX u_price_schedule_allow_only_one_effective_to_be_true
    IS 'В таблице может быть только одна запись со значением effective, равным TRUE';