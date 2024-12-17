package com.example.controllers.student;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Admin;
import com.example.model.Student;
import com.example.service.admin.AdminService;
import com.example.service.student.StudentService;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1")
public class StudentController {

    private StudentService studentservice;
    private AdminService adminservice;
    
    public StudentController(StudentService studentservice,AdminService adminservice) {
        this.studentservice = studentservice;
        this.adminservice = adminservice;
    }

    @GetMapping("/students")
    public List<Student> students(){
        return studentservice.getStudents();
    }

   
}
