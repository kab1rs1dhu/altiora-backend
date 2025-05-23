package com.altiora.altiora.service;

import com.altiora.altiora.DTO.ContentSectionDTO;
import com.altiora.altiora.DTO.ContentUpdateDTO;
import com.altiora.altiora.DTO.PageContentDTO;
import com.altiora.altiora.DTO.PageCreateDTO;
import com.altiora.altiora.model.ContentSection;
import com.altiora.altiora.model.Page;
import com.altiora.altiora.repository.ContentSectionRepository;
import com.altiora.altiora.repository.PageRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContentService {
    
    private final PageRepository pageRepository;
    private final ContentSectionRepository contentSectionRepository;
    
    @Autowired
    public ContentService(PageRepository pageRepository, ContentSectionRepository contentSectionRepository) {
        this.pageRepository = pageRepository;
        this.contentSectionRepository = contentSectionRepository;
    }
    
    @Transactional(readOnly = true)
    public Map<String, String> getPageContent(String pageName) {
        // Debug logging
        System.out.println("Getting content for page: " + pageName);
        
        List<ContentSection> sections = contentSectionRepository.findByPageName(pageName);
        System.out.println("Found " + sections.size() + " sections for page: " + pageName);
        
        Map<String, String> content = new HashMap<>();
        
        for (ContentSection section : sections) {
            content.put(section.getSectionKey(), section.getContent());
            System.out.println("Added section: " + section.getSectionKey());
        }
        
        return content;
    }
    
    @Transactional(readOnly = true)
    public PageContentDTO getFullPageContent(String pageName) {
        System.out.println("Getting full page content for: " + pageName);
        
        Page page = pageRepository.findByName(pageName)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + pageName));
        
        List<ContentSection> sections = contentSectionRepository.findByPageId(page.getId());
        System.out.println("Found " + sections.size() + " sections for page ID: " + page.getId());
        
        Map<String, String> sectionMap = new HashMap<>();
        
        for (ContentSection section : sections) {
            sectionMap.put(section.getSectionKey(), section.getContent());
            System.out.println("Section: " + section.getSectionKey() + " = " + 
                              (section.getContent().length() > 50 ? 
                               section.getContent().substring(0, 50) + "..." : 
                               section.getContent()));
        }
        
        return new PageContentDTO(
                page.getName(),
                page.getTitle(),
                page.getDescription(),
                sectionMap
        );
    }
    
    @Transactional(readOnly = true)
    public String getSectionContent(String pageName, String sectionKey) {
        System.out.println("Getting section content for: " + pageName + " -> " + sectionKey);
        
        return contentSectionRepository.findByPageNameAndSectionKey(pageName, sectionKey)
                .map(ContentSection::getContent)
                .orElse(null);
    }
    
    @Transactional
    public Page createPage(PageCreateDTO pageCreateDTO) {
        System.out.println("Creating page: " + pageCreateDTO.getName());
        
        if (pageRepository.existsByName(pageCreateDTO.getName())) {
            throw new IllegalArgumentException("Page with name " + pageCreateDTO.getName() + " already exists");
        }
        
        Page page = new Page();
        page.setName(pageCreateDTO.getName());
        page.setTitle(pageCreateDTO.getTitle());
        page.setDescription(pageCreateDTO.getDescription());
        
        Page savedPage = pageRepository.save(page);
        System.out.println("Saved page with ID: " + savedPage.getId());
        
        if (pageCreateDTO.getContentSections() != null) {
            for (ContentSectionDTO sectionDTO : pageCreateDTO.getContentSections()) {
                ContentSection section = new ContentSection();
                section.setPage(savedPage);
                section.setSectionKey(sectionDTO.getSectionKey());
                section.setContent(sectionDTO.getContent());
                section.setContentType(sectionDTO.getContentType());
                contentSectionRepository.save(section);
                System.out.println("Created section: " + sectionDTO.getSectionKey());
            }
        }
        
        return savedPage;
    }
    
    @Transactional
    public void updateContent(ContentUpdateDTO updateDTO) {
        System.out.println("Updating content for: " + updateDTO.getPageName() + " -> " + updateDTO.getSectionKey());
        
        Page page = pageRepository.findByName(updateDTO.getPageName())
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + updateDTO.getPageName()));
        
        ContentSection section = contentSectionRepository
                .findByPageNameAndSectionKey(updateDTO.getPageName(), updateDTO.getSectionKey())
                .orElse(new ContentSection());
        
        if (section.getId() == null) {
            section.setPage(page);
            section.setSectionKey(updateDTO.getSectionKey());
            System.out.println("Creating new section: " + updateDTO.getSectionKey());
        } else {
            System.out.println("Updating existing section: " + updateDTO.getSectionKey());
        }
        
        section.setContent(updateDTO.getContent());
        contentSectionRepository.save(section);
        System.out.println("Content updated successfully");
    }
    
    @Transactional
    public void deletePage(String pageName) {
        System.out.println("Deleting page: " + pageName);
        
        Page page = pageRepository.findByName(pageName)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + pageName));
        
        // Delete all content sections first (cascade should handle this, but let's be explicit)
        List<ContentSection> sections = contentSectionRepository.findByPageId(page.getId());
        contentSectionRepository.deleteAll(sections);
        System.out.println("Deleted " + sections.size() + " content sections");
        
        pageRepository.delete(page);
        System.out.println("Page deleted successfully");
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllPageNames() {
        List<String> pageNames = pageRepository.findAll().stream()
                .map(Page::getName)
                .collect(Collectors.toList());
        
        System.out.println("Found " + pageNames.size() + " pages: " + pageNames);
        return pageNames;
    }

    @Transactional
    public void addSectionToPage(String pageName, ContentSectionDTO sectionDTO) {
        System.out.println("Adding section to page: " + pageName + " -> " + sectionDTO.getSectionKey());
        
        Page page = pageRepository.findByName(pageName)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + pageName));
        
        // Check if section already exists
        boolean exists = contentSectionRepository.findByPageNameAndSectionKey(pageName, sectionDTO.getSectionKey()).isPresent();
        if (exists) {
            throw new IllegalArgumentException("Section with key '" + sectionDTO.getSectionKey() + "' already exists for page '" + pageName + "'");
        }
        
        ContentSection section = new ContentSection();
        section.setPage(page);
        section.setSectionKey(sectionDTO.getSectionKey());
        section.setContent(sectionDTO.getContent());
        section.setContentType(sectionDTO.getContentType());
        
        contentSectionRepository.save(section);
        System.out.println("Section added successfully");
    }

    @Transactional
    public void deleteSection(String pageName, String sectionKey) {
        System.out.println("Deleting section: " + pageName + " -> " + sectionKey);
        
        ContentSection section = contentSectionRepository
                .findByPageNameAndSectionKey(pageName, sectionKey)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Section with key '" + sectionKey + "' not found for page '" + pageName + "'"));
        
        contentSectionRepository.delete(section);
        System.out.println("Section deleted successfully");
    }
    
    /**
     * Initialize default pages if they don't exist
     */
    @Transactional
    public void initializeDefaultPages() {
        System.out.println("Initializing default pages...");
        
        String[] defaultPages = {
            "home", "seo", "ppc", "web-development", 
            "mobile-development", "lead-generation", 
            "appointment-setting", "contact"
        };
        
        for (String pageName : defaultPages) {
            if (!pageRepository.existsByName(pageName)) {
                Page page = new Page();
                page.setName(pageName);
                page.setTitle(formatTitle(pageName));
                page.setDescription("Description for " + formatTitle(pageName));
                pageRepository.save(page);
                System.out.println("Created default page: " + pageName);
            }
        }
    }
    
    private String formatTitle(String pageName) {
        return pageName.substring(0, 1).toUpperCase() + 
               pageName.substring(1).replace("-", " ");
    }
    
    /**
     * Get summary statistics
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPages", pageRepository.count());
        stats.put("totalSections", contentSectionRepository.count());
        
        return stats;
    }
    
    /**
     * Get page with sections count
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getPageWithSectionCount(String pageName) {
        Page page = pageRepository.findByName(pageName)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + pageName));
        
        List<ContentSection> sections = contentSectionRepository.findByPageId(page.getId());
        
        Map<String, Object> result = new HashMap<>();
        result.put("name", page.getName());
        result.put("title", page.getTitle());
        result.put("description", page.getDescription());
        result.put("sectionCount", sections.size());
        result.put("sections", sections.stream()
                .collect(Collectors.toMap(
                    ContentSection::getSectionKey,
                    ContentSection::getContent
                )));
        
        return result;
    }
}