CREATE TABLE modification_price (
    id SERIAL PRIMARY KEY,
    amount INTEGER NOT NULL,
    modification_sku VARCHAR NOT NULL,
    scale_nanomols INTEGER NOT NULL,
    price_schedule_id INTEGER NOT NULL REFERENCES price_schedule(id),
    UNIQUE (modification_sku, scale_nanomols, price_schedule_id)
);

COMMENT ON TABLE modification_price IS 'Цена модификации';
COMMENT ON COLUMN modification_price.id IS 'Идентификатор цены модификации';
COMMENT ON COLUMN modification_price.amount IS 'Значение цены';
COMMENT ON COLUMN modification_price.modification_sku IS 'Артикул модификатора';
COMMENT ON COLUMN modification_price.scale_nanomols IS 'Масштаб в наномолях';
COMMENT ON COLUMN modification_price.price_schedule_id  IS 'Идентификатор прейскуранта (FK)';