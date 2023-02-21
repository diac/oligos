CREATE TABLE synthesis (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    base_type VARCHAR NOT NULL,
    sku VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE synthesis IS 'Синтез';
COMMENT ON COLUMN synthesis.id IS 'Идентификатор синтеза';
COMMENT ON COLUMN synthesis.name IS 'Наименование синтеза';
COMMENT ON COLUMN synthesis.base_type IS 'Тип основания';
COMMENT ON COLUMN synthesis.sku IS 'Артикул синтеза';