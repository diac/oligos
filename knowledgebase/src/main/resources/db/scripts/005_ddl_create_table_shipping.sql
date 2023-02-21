CREATE TABLE shipping (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    formulation_id INTEGER REFERENCES formulation(id)
);

COMMENT ON TABLE shipping IS 'Тип доставки';
COMMENT ON COLUMN shipping.id IS 'Идентификатор типа доставки';
COMMENT ON COLUMN shipping.name IS 'Наименование типа доставки';
COMMENT ON COLUMN shipping.formulation_id IS 'Идентификатор типа препарата (FK)';