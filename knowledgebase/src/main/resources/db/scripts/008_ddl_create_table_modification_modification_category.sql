CREATE TABLE modification_modification_category (
    modification_id INTEGER REFERENCES modification(id),
    modification_category VARCHAR,
    PRIMARY KEY (modification_id, modification_category)
);

COMMENT ON TABLE modification_modification_category IS 'Связь между модификаторами и категориями модификаторов';
COMMENT ON COLUMN modification_modification_category.modification_id IS 'Идентификатор модификатора (FK)';
COMMENT ON COLUMN modification_modification_category.modification_category IS 'Категория модификатора';