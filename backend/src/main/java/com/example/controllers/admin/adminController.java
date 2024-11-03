package com.example.controllers.admin;

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
import com.example.service.student.studentService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class adminController {
    
    private adminService adminservice;
    private studentService studentservice;

    public adminController(adminService adminservice , studentService studentservice) {
        this.adminservice = adminservice;
        this.studentservice = studentservice;
    }

    @PostMapping("/admin")
    public Admin admin(@RequestBody adminRequest request){
        return adminservice.getAdmin(request.emailId());
    }

    record adminRequest(String emailId){}
    
    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin){
        try{
            Admin checkAdmin = adminservice.getAdmin(admin.getEmailId());
            if(checkAdmin != null){
                throw new Exception("Admin is already registered");
            }
            Student student = studentservice.getStudent(admin.getEmailId());
            if(student != null){
                throw new Exception("Already Student Exists");
            }
            Boolean response = adminservice.createAdmin(admin);
            if(!response){
                throw new Exception("Problem While Saving the admin");
            }
            return new ResponseEntity<String>("Registered",HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
