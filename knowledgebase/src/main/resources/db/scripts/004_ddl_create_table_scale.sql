CREATE TABLE scale (
    id SERIAL PRIMARY KEY,
    nanomols INTEGER UNIQUE NOT NULL,
    display_name VARCHAR NOT NULL
);

COMMENT ON TABLE scale IS 'Масштаб';
COMMENT ON COLUMN scale.id IS 'Идентификатор масштаба';
COMMENT ON COLUMN scale.nanomols IS 'Значение в наномолях';
COMMENT ON COLUMN scale.display_name IS 'Отображаемое наименование';