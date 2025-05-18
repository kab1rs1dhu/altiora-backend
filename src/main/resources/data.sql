-- Insert sample pages if they don't exist
INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'home', 'Home', 'Welcome to Altiora Marketing Agency', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'home');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'seo', 'Search Engine Optimization', 'Drive organic traffic with our SEO services', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'seo');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'ppc', 'PPC Advertising', 'Drive immediate results with pay-per-click', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'ppc');

-- Insert sample content sections
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Search Engine Optimization', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'seo' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Drive organic traffic and boost your website''s visibility with our data-driven SEO strategies.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'seo' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');