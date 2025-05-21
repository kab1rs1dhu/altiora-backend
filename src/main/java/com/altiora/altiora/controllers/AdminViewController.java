package com.altiora.altiora.controllers;

import com.altiora.altiora.DTO.ContentSectionDTO;
import com.altiora.altiora.DTO.ContentUpdateDTO;
import com.altiora.altiora.DTO.PageContentDTO;
import com.altiora.altiora.DTO.PageCreateDTO;
import com.altiora.altiora.model.ContentSection.ContentType;
import com.altiora.altiora.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    private final ContentService contentService;
    
    @Autowired
    public AdminViewController(ContentService contentService) {
        this.contentService = contentService;
    }
    
    @GetMapping("/login")
    public String login() {
        return "admin/login";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<String> pageNames = contentService.getAllPageNames();
        model.addAttribute("pageNames", pageNames);
        return "admin/dashboard";
    }
    
    @GetMapping("/pages")
    public String allPages(Model model) {
        List<String> pageNames = contentService.getAllPageNames();
        model.addAttribute("pageNames", pageNames);
        return "admin/pages";
    }
    
    @GetMapping("/pages/{pageName}")
    public String editPage(@PathVariable String pageName, Model model) {
        try {
            PageContentDTO pageContent = contentService.getFullPageContent(pageName);
            model.addAttribute("page", pageContent);
            return "admin/edit-page";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin/error";
        }
    }
    
    @GetMapping("/pages/new")
    public String newPage(Model model) {
        model.addAttribute("page", new PageCreateDTO());
        model.addAttribute("contentTypes", ContentType.values());
        return "admin/new-page";
    }
    
    @PostMapping("/pages/new")
    public String createPage(@ModelAttribute PageCreateDTO page) {
        try {
            contentService.createPage(page);
            return "redirect:/admin/pages";
        } catch (Exception e) {
            return "redirect:/admin/pages/new?error=" + e.getMessage();
        }
    }
    
    @GetMapping("/content/edit")
    public String editContent(@RequestParam String pageName, @RequestParam String sectionKey, Model model) {
        String content = contentService.getSectionContent(pageName, sectionKey);
        ContentUpdateDTO updateDTO = new ContentUpdateDTO(pageName, sectionKey, content);
        model.addAttribute("content", updateDTO);
        return "admin/edit-content";
    }
    
    @PostMapping("/content/edit")
    public String updateContent(@ModelAttribute ContentUpdateDTO content) {
        try {
            contentService.updateContent(content);
            return "redirect:/admin/pages/" + content.getPageName();
        } catch (Exception e) {
            return "redirect:/admin/content/edit?pageName=" + content.getPageName() 
                + "&sectionKey=" + content.getSectionKey() 
                + "&error=" + e.getMessage();
        }
    }

    // Add to AdminViewController.java

@GetMapping("/pages/{pageName}/sections/new")
public String newSection(@PathVariable String pageName, Model model) {
    model.addAttribute("pageName", pageName);
    return "admin/new-section";
}

@PostMapping("/login")
public String login(@RequestParam String username, @RequestParam String password) {
    // Implement your authentication logic here
    if (username.equals("admin") && password.equals("password")) {
        return "redirect:/admin/dashboard";
    } else {
        return "redirect:/admin/login?error=Invalid credentials";
    }
}
}
