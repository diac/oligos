CREATE TABLE modification (
    id SERIAL PRIMARY KEY,
    name VARCHAR UNIQUE NOT NULL,
    code VARCHAR UNIQUE NOT NULL,
    sku VARCHAR UNIQUE NOT NULL
);

COMMENT ON TABLE modification IS 'Модификатор';
COMMENT ON COLUMN modification.id IS 'Идентификатор модификатора';
COMMENT ON COLUMN modification.name IS 'Наименование модификатора';
COMMENT ON COLUMN modification.code IS 'Код модификатора';
COMMENT ON COLUMN modification.sku IS 'Артикул модификатора';