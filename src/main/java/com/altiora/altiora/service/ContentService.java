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
        List<ContentSection> sections = contentSectionRepository.findByPageName(pageName);
        Map<String, String> content = new HashMap<>();
        
        for (ContentSection section : sections) {
            content.put(section.getSectionKey(), section.getContent());
        }
        
        return content;
    }
    
    @Transactional(readOnly = true)
    public PageContentDTO getFullPageContent(String pageName) {
        Page page = pageRepository.findByName(pageName)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + pageName));
        
        List<ContentSection> sections = contentSectionRepository.findByPageId(page.getId());
        Map<String, String> sectionMap = new HashMap<>();
        
        for (ContentSection section : sections) {
            sectionMap.put(section.getSectionKey(), section.getContent());
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
        return contentSectionRepository.findByPageNameAndSectionKey(pageName, sectionKey)
                .map(ContentSection::getContent)
                .orElse(null);
    }
    
    @Transactional
    public Page createPage(PageCreateDTO pageCreateDTO) {
        if (pageRepository.existsByName(pageCreateDTO.getName())) {
            throw new IllegalArgumentException("Page with name " + pageCreateDTO.getName() + " already exists");
        }
        
        Page page = new Page();
        page.setName(pageCreateDTO.getName());
        page.setTitle(pageCreateDTO.getTitle());
        page.setDescription(pageCreateDTO.getDescription());
        
        Page savedPage = pageRepository.save(page);
        
        if (pageCreateDTO.getContentSections() != null) {
            for (ContentSectionDTO sectionDTO : pageCreateDTO.getContentSections()) {
                ContentSection section = new ContentSection();
                section.setPage(savedPage);
                section.setSectionKey(sectionDTO.getSectionKey());
                section.setContent(sectionDTO.getContent());
                section.setContentType(sectionDTO.getContentType());
                contentSectionRepository.save(section);
            }
        }
        
        return savedPage;
    }
    
    @Transactional
    public void updateContent(ContentUpdateDTO updateDTO) {
        Page page = pageRepository.findByName(updateDTO.getPageName())
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + updateDTO.getPageName()));
        
        ContentSection section = contentSectionRepository
                .findByPageNameAndSectionKey(updateDTO.getPageName(), updateDTO.getSectionKey())
                .orElse(new ContentSection());
        
        if (section.getId() == null) {
            section.setPage(page);
            section.setSectionKey(updateDTO.getSectionKey());
        }
        
        section.setContent(updateDTO.getContent());
        contentSectionRepository.save(section);
    }
    
    @Transactional
    public void deletePage(String pageName) {
        Page page = pageRepository.findByName(pageName)
                .orElseThrow(() -> new EntityNotFoundException("Page not found with name: " + pageName));
        
        pageRepository.delete(page);
    }
    
    @Transactional(readOnly = true)
    public List<String> getAllPageNames() {
        return pageRepository.findAll().stream()
                .map(Page::getName)
                .collect(Collectors.toList());
    }
}
