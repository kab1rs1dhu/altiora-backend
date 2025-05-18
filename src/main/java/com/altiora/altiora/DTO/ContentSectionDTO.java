package com.altiora.altiora.DTO;


import com.altiora.altiora.model.ContentSection.ContentType;

public class ContentSectionDTO {
    private String sectionKey;
    private String content;
    private ContentType contentType;

    public ContentSectionDTO() {
    }

    public ContentSectionDTO(String sectionKey, String content, ContentType contentType) {
        this.sectionKey = sectionKey;
        this.content = content;
        this.contentType = contentType;
    }

    // Getters and Setters
    public String getSectionKey() {
        return sectionKey;
    }

    public void setSectionKey(String sectionKey) {
        this.sectionKey = sectionKey;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
}