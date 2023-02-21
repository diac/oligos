CREATE TABLE modification_purification (
    id SERIAL PRIMARY KEY,
    modification_id INTEGER NOT NULL REFERENCES modification(id),
    purification_id INTEGER NOT NULL REFERENCES purification(id),
    UNIQUE(modification_id, purification_id)
);

COMMENT ON TABLE modification_purification IS 'Связь между модификаторами и типами очистки';
COMMENT ON COLUMN modification_purification.id IS 'Идентификатор связи';
COMMENT ON COLUMN modification_purification.modification_id IS 'Идентификатор модификатора';
COMMENT ON COLUMN modification_purification.purification_id IS 'Идентификатор типа очистки';