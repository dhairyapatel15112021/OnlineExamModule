package com.example.repository.test;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Test;

public interface TestRepository extends JpaRepository<Test,Integer> {

    @Query("SELECT t from Test t JOIN Student s on t.batch.id = s.batch.id where s.id = :studentId")
    List<Test> findByStudentId(@Param("studentId") int studentId);
    
    Test findByTitle(String title);
}
