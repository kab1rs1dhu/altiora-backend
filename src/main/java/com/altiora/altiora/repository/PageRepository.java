package com.altiora.altiora.repository;

import com.altiora.altiora.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findByName(String name);
    boolean existsByName(String name);
}
