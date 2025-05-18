package com.altiora.altiora.DTO;

import java.util.Map;

public class PageContentDTO {
    private String name;
    private String title;
    private String description;
    private Map<String, String> contentSections;

    public PageContentDTO() {
    }

    public PageContentDTO(String name, String title, String description, Map<String, String> contentSections) {
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

    public Map<String, String> getContentSections() {
        return contentSections;
    }

    public void setContentSections(Map<String, String> contentSections) {
        this.contentSections = contentSections;
    }
}
