package com.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "languages",uniqueConstraints = @UniqueConstraint(columnNames = "language_id"))
public class Languages {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "language_name" , nullable = false , unique = true)
    private String languageName;

    @Column(name = "language_id" , nullable = false)
    private int languageId;

    public Languages(int id, String languageName, int languageId) {
        this.id = id;
        this.languageName = languageName;
        this.languageId = languageId;
    }

    public Languages() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((languageName == null) ? 0 : languageName.hashCode());
        result = prime * result + languageId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Languages other = (Languages) obj;
        if (id != other.id)
            return false;
        if (languageName == null) {
            if (other.languageName != null)
                return false;
        } else if (!languageName.equals(other.languageName))
            return false;
        if (languageId != other.languageId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Languages [id=" + id + ", languageName=" + languageName + ", languageId=" + languageId + "]";
    }
    
}
