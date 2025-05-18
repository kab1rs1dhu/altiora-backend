package com.altiora.altiora.controllers;

import com.altiora.altiora.DTO.ContentUpdateDTO;
import com.altiora.altiora.DTO.PageCreateDTO;
import com.altiora.altiora.DTO.PageContentDTO;
import com.altiora.altiora.service.ContentService;

import jakarta.persistence.EntityExistsException;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/content")
@CrossOrigin(origins = "http://localhost:3000")
public class ContentController {
    
    private final ContentService contentService;
    
    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }
    
    @GetMapping("/page/{pageName}")
    public ResponseEntity<?> getPageContent(@PathVariable String pageName) {
        try {
            Map<String, String> content = contentService.getPageContent(pageName);
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/section/{pageName}/{sectionKey}")
    public ResponseEntity<?> getSectionContent(
            @PathVariable String pageName,
            @PathVariable String sectionKey) {
        try {
            String content = contentService.getSectionContent(pageName, sectionKey);
            if (content == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Section not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/pages")
    public ResponseEntity<List<String>> getAllPageNames() {
        List<String> pageNames = contentService.getAllPageNames();
        return ResponseEntity.ok(pageNames);
    }
}