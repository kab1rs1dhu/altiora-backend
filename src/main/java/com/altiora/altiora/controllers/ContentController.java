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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ContentController {
    
    private final ContentService contentService;
    
    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }
    
    @GetMapping("/page/{pageName}")
    public ResponseEntity<?> getPageContent(@PathVariable String pageName) {
        try {
            System.out.println("API Request: Getting content for page - " + pageName);
            Map<String, String> content = contentService.getPageContent(pageName);
            System.out.println("API Response: Found " + content.size() + " sections");
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/page/{pageName}/details")
    public ResponseEntity<?> getPageDetails(@PathVariable String pageName) {
        try {
            System.out.println("API Request: Getting page details for - " + pageName);
            Map<String, Object> details = contentService.getPageWithSectionCount(pageName);
            return ResponseEntity.ok(details);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Page not found: " + pageName);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("API Request: Getting section - " + pageName + "/" + sectionKey);
            String content = contentService.getSectionContent(pageName, sectionKey);
            if (content == null) {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Section not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            return ResponseEntity.ok(content);
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @GetMapping("/pages")
    public ResponseEntity<List<String>> getAllPageNames() {
        try {
            System.out.println("API Request: Getting all page names");
            List<String> pageNames = contentService.getAllPageNames();
            System.out.println("API Response: Found pages - " + pageNames);
            return ResponseEntity.ok(pageNames);
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of());
        }
    }
    
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        try {
            Map<String, Object> stats = contentService.getStatistics();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PostMapping("/debug/init")
    public ResponseEntity<?> initializeDefaults() {
        try {
            System.out.println("API Request: Initializing default data");
            contentService.initializeDefaultPages();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Default pages initialized");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.err.println("API Error: " + e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}