package com.example.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{
    Student findByEmailId(String email_id);
}
