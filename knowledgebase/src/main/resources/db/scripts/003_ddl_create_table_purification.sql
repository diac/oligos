CREATE TABLE purification (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    sku VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE purification IS 'Тип очистки';
COMMENT ON COLUMN purification.id IS 'Идентификатор типа очистки';
COMMENT ON COLUMN purification.name IS 'Наименование типа очистки';
COMMENT ON COLUMN purification.sku IS 'Артикул типа очистки';