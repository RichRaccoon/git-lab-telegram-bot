ALTER TABLE IF EXISTS approvals
    ADD COLUMN is_delete BOOLEAN DEFAULT FALSE;

ALTER TABLE IF EXISTS approvals
    ALTER COLUMN is_delete SET NOT NULL;