package com.altiora.altiora.DTO;

import java.util.List;

public class PageCreateDTO {
    private String name;
    private String title;
    private String description;
    private List<ContentSectionDTO> contentSections;

    public PageCreateDTO() {
    }

    public PageCreateDTO(String name, String title, String description, List<ContentSectionDTO> contentSections) {
        this.name = name;
        this.title = title;
        this.description = description;
        this.contentSections = contentSections;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ContentSectionDTO> getContentSections() {
        return contentSections;
    }

    public void setContentSections(List<ContentSectionDTO> contentSections) {
        this.contentSections = contentSections;
    }
}