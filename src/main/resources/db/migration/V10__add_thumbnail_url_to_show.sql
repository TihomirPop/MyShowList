-- Add nullable column
ALTER TABLE show ADD COLUMN thumbnail_url TEXT;

-- Set default for existing shows
UPDATE show
SET thumbnail_url = 'https://placeholder.example.com/thumbnail.png'
WHERE thumbnail_url IS NULL;

-- Add comment
COMMENT ON COLUMN show.thumbnail_url IS 'URL to show thumbnail image';
