package com.example.service.language;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Languages;
import com.example.repository.language.LanguageRepository;

@Service
public class LanguageService {
    
    private final LanguageRepository languageRepository;

    public LanguageService(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    public boolean registerLanguages(List<Languages> languages){
        try{
            languageRepository.saveAll(languages);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public Languages getLanguage(int languageId) {
       return languageRepository.findByLanguageId(languageId);
    }
}
