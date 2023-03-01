CREATE TABLE formulation_price (
    id SERIAL PRIMARY KEY,
    amount INTEGER NOT NULL,
    formulation_sku VARCHAR NOT NULL,
    price_schedule_id INTEGER NOT NULL REFERENCES price_schedule(id),
    UNIQUE(formulation_sku, price_schedule_id)
);

COMMENT ON TABLE formulation_price IS 'Цена типа препарата';
COMMENT ON COLUMN formulation_price.id is 'Идентификатор цены типа препарата';
COMMENT ON COLUMN formulation_price.amount IS 'Значение цены';
COMMENT ON COLUMN formulation_price.formulation_sku IS 'Артикул типа препарата';
COMMENT ON COLUMN formulation_price.price_schedule_id IS 'Идентификатор прейскуранта (FK)';