-- First, let's make sure we have all the pages
INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'home', 'Home', 'Welcome to Altiora Marketing Agency', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'home');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'seo', 'Search Engine Optimization', 'Drive organic traffic with our SEO services', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'seo');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'ppc', 'PPC Advertising', 'Drive immediate results with pay-per-click', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'ppc');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'web-development', 'Web Development', 'Create stunning, high-performing websites', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'web-development');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'mobile-development', 'Mobile Development', 'Build innovative mobile applications', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'mobile-development');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'lead-generation', 'Lead Generation', 'Capture high-quality leads with proven strategies', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'lead-generation');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'appointment-setting', 'Appointment Setting', 'Fill your calendar with qualified prospects', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'appointment-setting');

INSERT INTO pages (name, title, description, created_at, updated_at)
SELECT 'contact', 'Contact Us', 'Get in touch with our team', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM pages WHERE name = 'contact');

-- Now let's add some sample content sections for each page

-- HOME PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Elevate Your Business with Strategic Digital Marketing', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'home' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroSubtitle', 'Altiora helps businesses grow through custom digital strategies, innovative development, and data-driven marketing solutions.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'home' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroSubtitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'servicesTitle', 'Our Services', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'home' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'servicesTitle');

-- SEO PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Search Engine Optimization', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'seo' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Drive organic traffic and boost your website''s visibility with our data-driven SEO strategies.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'seo' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'overviewTitle', 'Elevate Your Online Presence', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'seo' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'overviewTitle');

-- PPC PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'PPC Advertising', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'ppc' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Drive targeted traffic and generate immediate results with our data-driven pay-per-click campaigns.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'ppc' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

-- WEB DEVELOPMENT PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Web Development', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'web-development' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Create stunning, high-performing websites that convert visitors into customers.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'web-development' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

-- MOBILE DEVELOPMENT PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Mobile App Development', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'mobile-development' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Build innovative mobile applications that engage users and drive business growth.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'mobile-development' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

-- LEAD GENERATION PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Lead Generation', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'lead-generation' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Capture high-quality leads with proven strategies that fill your sales pipeline.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'lead-generation' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

-- APPOINTMENT SETTING PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Appointment Setting', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'appointment-setting' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'Fill your calendar with qualified prospects through our professional appointment setting services.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'appointment-setting' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

-- CONTACT PAGE SECTIONS
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroTitle', 'Get In Touch', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'contact' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroTitle');

INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'heroDescription', 'We''d love to hear from you. Let''s discuss how we can help grow your business.', 'TEXT', NOW(), NOW()
FROM pages p WHERE p.name = 'contact' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'heroDescription');

-- Add some sample JSON content for services
INSERT INTO content_sections (page_id, section_key, content, content_type, created_at, updated_at)
SELECT p.id, 'services', '[
  {
    "icon": "fa-solid fa-magnifying-glass",
    "title": "SEO Audit & Strategy",
    "description": "We analyze your website''s current performance and create a comprehensive SEO strategy."
  },
  {
    "icon": "fa-solid fa-wrench",
    "title": "Technical SEO",
    "description": "We optimize your website''s technical aspects for search engines."
  },
  {
    "icon": "fa-solid fa-key",
    "title": "Keyword Research",
    "description": "We identify high-value keywords for your target audience."
  }
]', 'JSON', NOW(), NOW()
FROM pages p WHERE p.name = 'seo' 
AND NOT EXISTS (SELECT 1 FROM content_sections cs WHERE cs.page_id = p.id AND cs.section_key = 'services');