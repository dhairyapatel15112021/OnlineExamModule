package com.example.repository.language;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Languages;

public interface LanguageRepository extends JpaRepository<Languages,Integer> {
    
    Languages findByLanguageId(int id);
}
