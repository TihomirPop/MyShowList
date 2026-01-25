-- Make column NOT NULL after data migration
ALTER TABLE show
ALTER COLUMN thumbnail_url SET NOT NULL;

-- Add constraint for URL format validation
ALTER TABLE show
ADD CONSTRAINT show_thumbnail_url_format
CHECK (thumbnail_url ~ '^https?://.*');
