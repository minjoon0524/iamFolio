package com.inhatc.portfolio.repository;

import com.inhatc.portfolio.entity.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectFileRepository extends JpaRepository<ProjectFile,Long> {
}
