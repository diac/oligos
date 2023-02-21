CREATE TABLE formulation (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE UNIQUE NOT NULL,
    available BOOLEAN,
    sku VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE formulation IS 'Тип препарата';
COMMENT ON COLUMN formulation.id IS 'Идентификатор типа препарата';
COMMENT ON COLUMN formulation.name IS 'Наименование типа препарата';
COMMENT ON COLUMN formulation.available IS 'Признак доступности типа препарата';
COMMENT ON COLUMN formulation.sku IS 'Артикул типа препарата';