package com.altiora.altiora.DTO;

public class ContentUpdateDTO {
    private String pageName;
    private String sectionKey;
    private String content;

    public ContentUpdateDTO() {
    }

    public ContentUpdateDTO(String pageName, String sectionKey, String content) {
        this.pageName = pageName;
        this.sectionKey = sectionKey;
        this.content = content;
    }

    // Getters and Setters
    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

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
}