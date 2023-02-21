CREATE TABLE shipping (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE shipping IS 'Тип доставки';
COMMENT ON COLUMN shipping.id IS 'Идентификатор типа доставки';
COMMENT ON COLUMN shipping.name IS 'Наименование типа доставки';