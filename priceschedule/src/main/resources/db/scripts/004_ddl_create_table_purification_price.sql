CREATE TABLE purification_price (
    id SERIAL PRIMARY KEY,
    amount INTEGER NOT NULL,
    purification_sku VARCHAR NOT NULL,
    base_type VARCHAR NOT NULL,
    scale_nanomols INTEGER NOT NULL,
    price_schedule_id INTEGER NOT NULL REFERENCES price_schedule(id),
    UNIQUE (purification_sku, base_type, scale_nanomols, price_schedule_id)
);

COMMENT ON TABLE purification_price IS 'Цена типа очистки';
COMMENT ON COLUMN purification_price.id IS 'Идентификатор цены типа очистки';
COMMENT ON COLUMN purification_price.amount IS 'Значение цены';
COMMENT ON COLUMN purification_price.purification_sku IS 'Артикул типа очистки';
COMMENT ON COLUMN purification_price.base_type IS 'Тип основания';
COMMENT ON COLUMN purification_price.scale_nanomols IS 'Масштаб в наномолях';
COMMENT ON COLUMN purification_price.price_schedule_id IS 'Идентификатор прейскуранта (FK)';