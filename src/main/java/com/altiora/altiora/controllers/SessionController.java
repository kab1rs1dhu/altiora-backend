package com.altiora.altiora.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/session")
public class SessionController {
    
    @GetMapping("/status")
    public ResponseEntity<?> getSessionStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = auth != null && auth.isAuthenticated() && 
                                 !auth.getAuthorities().stream()
                                     .anyMatch(a -> a.getAuthority().equals("ROLE_ANONYMOUS"));
        
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", isAuthenticated);
        if (isAuthenticated) {
            response.put("username", auth.getName());
        }
        
        return ResponseEntity.ok(response);
    }
}
