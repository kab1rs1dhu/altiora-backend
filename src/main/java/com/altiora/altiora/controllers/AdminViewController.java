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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        try {
            List<String> pageNames = contentService.getAllPageNames();
            var stats = contentService.getStatistics();
            
            model.addAttribute("pageNames", pageNames);
            model.addAttribute("totalPages", stats.get("totalPages"));
            model.addAttribute("totalSections", stats.get("totalSections"));
            
            System.out.println("Dashboard loaded with " + pageNames.size() + " pages");
            return "admin/dashboard";
        } catch (Exception e) {
            System.err.println("Error loading dashboard: " + e.getMessage());
            model.addAttribute("error", "Error loading dashboard: " + e.getMessage());
            return "admin/error";
        }
    }
    
    @GetMapping("/pages")
    public String allPages(Model model) {
        try {
            List<String> pageNames = contentService.getAllPageNames();
            model.addAttribute("pageNames", pageNames);
            
            System.out.println("Pages view loaded with: " + pageNames);
            return "admin/pages";
        } catch (Exception e) {
            System.err.println("Error loading pages: " + e.getMessage());
            model.addAttribute("error", "Error loading pages: " + e.getMessage());
            return "admin/error";
        }
    }
    
    @GetMapping("/pages/{pageName}")
    public String editPage(@PathVariable String pageName, Model model) {
        try {
            System.out.println("Loading edit page for: " + pageName);
            PageContentDTO pageContent = contentService.getFullPageContent(pageName);
            model.addAttribute("page", pageContent);
            
            System.out.println("Page content loaded: " + pageContent.getName() + 
                              " with " + pageContent.getContentSections().size() + " sections");
            return "admin/edit-page";
        } catch (Exception e) {
            System.err.println("Error loading page " + pageName + ": " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "Error loading page: " + e.getMessage());
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
    public String createPage(@ModelAttribute PageCreateDTO page, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Creating new page: " + page.getName());
            contentService.createPage(page);
            redirectAttributes.addFlashAttribute("success", "Page created successfully!");
            return "redirect:/admin/pages";
        } catch (Exception e) {
            System.err.println("Error creating page: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/pages/new";
        }
    }
    
    @GetMapping("/content/edit")
    public String editContent(@RequestParam String pageName, @RequestParam String sectionKey, Model model) {
        try {
            System.out.println("Loading content editor for: " + pageName + " -> " + sectionKey);
            String content = contentService.getSectionContent(pageName, sectionKey);
            
            if (content == null) {
                content = ""; // Default empty content for new sections
            }
            
            ContentUpdateDTO updateDTO = new ContentUpdateDTO(pageName, sectionKey, content);
            model.addAttribute("content", updateDTO);
            
            return "admin/edit-content";
        } catch (Exception e) {
            System.err.println("Error loading content editor: " + e.getMessage());
            model.addAttribute("error", "Error loading content: " + e.getMessage());
            return "admin/error";
        }
    }
    
    @PostMapping("/content/edit")
    public String updateContent(@ModelAttribute ContentUpdateDTO content, RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Updating content: " + content.getPageName() + " -> " + content.getSectionKey());
            contentService.updateContent(content);
            redirectAttributes.addFlashAttribute("success", "Content updated successfully!");
            return "redirect:/admin/pages/" + content.getPageName();
        } catch (Exception e) {
            System.err.println("Error updating content: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/content/edit?pageName=" + content.getPageName() 
                + "&sectionKey=" + content.getSectionKey();
        }
    }

    @GetMapping("/pages/{pageName}/sections/new")
    public String newSection(@PathVariable String pageName, Model model) {
        model.addAttribute("pageName", pageName);
        model.addAttribute("contentTypes", ContentType.values());
        return "admin/new-section";
    }
    
    @GetMapping("/debug/reinit")
    public String reinitializeData(RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Re-initializing data...");
            contentService.initializeDefaultPages();
            redirectAttributes.addFlashAttribute("success", "Data re-initialized successfully!");
        } catch (Exception e) {
            System.err.println("Error re-initializing data: " + e.getMessage());
            redirectAttributes.addFlashAttribute("error", "Error re-initializing data: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}