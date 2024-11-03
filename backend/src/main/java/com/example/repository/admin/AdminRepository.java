package com.example.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Admin;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findByEmailId(String emailId);
}
