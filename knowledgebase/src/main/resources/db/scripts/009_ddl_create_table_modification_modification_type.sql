CREATE TABLE modification_modification_type (
    modification_id INTEGER REFERENCES modification(id),
    modification_type VARCHAR,
    PRIMARY KEY (modification_id, modification_type)
);

COMMENT ON TABLE modification_modification_type IS 'Связь между модификаторами и типамми модификаторов';
COMMENT ON COLUMN modification_modification_type.modification_id IS 'Идентификатор модификатора (FK)';
COMMENT ON COLUMN  modification_modification_type.modification_type IS 'Тип модификатора';