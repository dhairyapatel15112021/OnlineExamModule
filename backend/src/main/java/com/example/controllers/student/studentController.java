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
import com.example.service.admin.adminService;
import com.example.service.student.studentService;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/v1")
public class studentController {

    private studentService studentservice;
    private adminService adminservice;
    
    public studentController(studentService studentservice,adminService adminservice) {
        this.studentservice = studentservice;
        this.adminservice = adminservice;
    }

    @GetMapping("/students")
    public List<Student> students(){
        return studentservice.getStudents();
    }

    @PostMapping("/student")
    public Student student(@RequestBody studentRequest request){
        return studentservice.getStudent(request.emailId());
    }

    record studentRequest(String emailId){}

    @PostMapping("/student/register")
    public ResponseEntity<String> createStudent(@RequestBody Student student){
        try{
            Student checkStudent = studentservice.getStudent(student.getEmailId());
            if(checkStudent != null){
                throw new Exception("Student Already Registed");
            }
            Admin checkAdmin = adminservice.getAdmin(student.getEmailId());
            if(checkAdmin != null){
                throw new Exception("Exist with this email id");
            }
            Boolean response = studentservice.createStudent(student);
            if(!response){
                throw new Exception("Problem While saving the Student");
            }
            return new ResponseEntity<String>("Registered", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
