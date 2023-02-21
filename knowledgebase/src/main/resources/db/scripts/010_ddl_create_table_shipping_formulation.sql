CREATE TABLE shipping_formulation (
    shipping_id INTEGER REFERENCES shipping(id),
    formulation_id INTEGER REFERENCES formulation(id),
    PRIMARY KEY (shipping_id, formulation_id)
);

COMMENT ON TABLE shipping_formulation IS 'Связь между типами доставки и типами препаратов';
COMMENT ON COLUMN shipping_formulation.shipping_id IS 'Идентификатор типа доставки (FK)';
COMMENT ON COLUMN shipping_formulation.formulation_id IS 'Идентификатор типа препарата (FK)';