package com.altiora.altiora.controllers;

import com.altiora.altiora.DTO.ContentSectionDTO;
import com.altiora.altiora.DTO.ContentUpdateDTO;
import com.altiora.altiora.DTO.PageCreateDTO;
import com.altiora.altiora.model.Page;
import com.altiora.altiora.service.ContentService;



import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {
    
    private final ContentService contentService;
    
    @Autowired
    public AdminController(ContentService contentService) {
        this.contentService = contentService;
    }
    
    @PostMapping("/page")
    public ResponseEntity<?> createPage(@RequestBody PageCreateDTO pageCreateDTO) {
        try {
            Page page = contentService.createPage(pageCreateDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("pageId", page.getId());
            response.put("pageName", page.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @PutMapping("/content")
    public ResponseEntity<?> updateContent(@RequestBody ContentUpdateDTO updateDTO) {
        try {
            contentService.updateContent(updateDTO);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    
    @DeleteMapping("/page/{pageName}")
    public ResponseEntity<?> deletePage(@PathVariable String pageName) {
        try {
            contentService.deletePage(pageName);
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } catch (Exception e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // src/main/java/com/altiora/altiora/controllers/AdminController.java (update)

// Add this method to the existing AdminController
@PostMapping("/section")
public ResponseEntity<?> createSection(@RequestBody ContentSectionDTO sectionDTO, 
                                      @RequestParam String pageName) {
    try {
        contentService.addSectionToPage(pageName, sectionDTO);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } catch (EntityNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}

// Add to AdminController.java

@DeleteMapping("/section")
public ResponseEntity<?> deleteSection(@RequestParam String pageName, 
                                      @RequestParam String sectionKey) {
    try {
        contentService.deleteSection(pageName, sectionKey);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        return ResponseEntity.ok(response);
    } catch (EntityNotFoundException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    } catch (Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
}