CREATE TABLE synthesis_price (
    id SERIAL PRIMARY KEY,
    amount INTEGER NOT NULL,
    synthesis_sku VARCHAR NOT NULL,
    scale_nanomols INTEGER NOT NULL,
    price_schedule_id INTEGER NOT NULL REFERENCES price_schedule(id),
    UNIQUE (synthesis_sku, scale_nanomols, price_schedule_id)
);

COMMENT ON TABLE synthesis_price IS 'Цена синтеза';
COMMENT ON COLUMN synthesis_price.id IS 'Идентификатор цены синтеза';
COMMENT ON COLUMN synthesis_price.amount IS 'Значение цены';
COMMENT ON COLUMN synthesis_price.synthesis_sku IS 'Артикул синтеза';
COMMENT ON COLUMN synthesis_price.scale_nanomols IS 'Масштаб в наномолях';
COMMENT ON COLUMN synthesis_price.price_schedule_id IS 'Идентификатор прейскуранта (FK)';