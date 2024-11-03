package com.example.controllers.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Admin;
import com.example.model.Student;
import com.example.service.admin.adminService;
import com.example.service.jwt.jwtService;
import com.example.service.student.studentService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class authenticationController {
    
    private adminService adminservice;
    private studentService studentservice;
    private jwtService jwtservice;

    public authenticationController(adminService adminservice, studentService studentservice , jwtService jwtservice) {
        this.adminservice = adminservice;
        this.studentservice = studentservice;
        this.jwtservice = jwtservice;
    }

    @PostMapping("/login")
    public ResponseEntity<String> auth(@RequestBody loginRequest request){
       try{
        Student student = studentservice.getStudent(request.emailId());
        Admin admin = null;
        if(student == null){
            admin = adminservice.getAdmin(request.emailId());
        }
        if(student == null && admin == null){
            throw new Exception("Invalid Login Credientials");
        }
        if(student != null && !student.getPassword().equals(request.password())){
            throw new Exception("Invalid Login Credientials");
        }
        if(admin != null && !admin.getPassword().equals(request.password())){
            throw new Exception("Invalid Login Credientials");
        }
        return new ResponseEntity<>(jwtservice.generateToken(request.emailId()),HttpStatus.OK);
       }
       catch(Exception e){
        return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
    record loginRequest(String emailId,String password){}
}
