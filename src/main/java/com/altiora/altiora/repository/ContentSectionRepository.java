package com.altiora.altiora.repository;

import com.altiora.altiora.model.ContentSection;
import com.altiora.altiora.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContentSectionRepository extends JpaRepository<ContentSection, Long> {
    List<ContentSection> findByPageId(Long pageId);
    
    @Query("SELECT cs FROM ContentSection cs JOIN cs.page p WHERE p.name = :pageName")
    List<ContentSection> findByPageName(String pageName);
    
    @Query("SELECT cs FROM ContentSection cs JOIN cs.page p WHERE p.name = :pageName AND cs.sectionKey = :sectionKey")
    Optional<ContentSection> findByPageNameAndSectionKey(String pageName, String sectionKey);
}