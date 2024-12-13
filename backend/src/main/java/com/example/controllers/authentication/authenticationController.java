package com.example.controllers.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Admin;
import com.example.model.Student;
import com.example.service.admin.adminService;
import com.example.service.college.collegeService;
import com.example.service.jwt.jwtService;
import com.example.service.student.studentService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class authenticationController {

    private final adminService adminservice;
    private final studentService studentservice;
    private final collegeService collegeService;
    private final jwtService jwtservice;
    private final AuthenticationManager authenticationManager;

    public authenticationController(adminService adminservice, studentService studentservice, jwtService jwtservice,collegeService collegeService,
            AuthenticationManager authenticationManager) {
        this.adminservice = adminservice;
        this.studentservice = studentservice;
        this.jwtservice = jwtservice;
        this.authenticationManager = authenticationManager;
        this.collegeService = collegeService;
    }

    // @PostMapping("/admin")
    public Admin admin(@RequestBody adminRequest request){
        return adminservice.getAdmin(request.emailId());
    }

    record adminRequest(String emailId){}
    
    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin){
        try{
            if(studentservice.getStudent(admin.getEmailId()) != null || adminservice.getAdmin(admin.getEmailId()) != null || collegeService.getCollege(admin.getEmailId()) != null){
                throw new Exception("Exist with this email id");
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

    @PostMapping("/login")
    public ResponseEntity<String> authLogin(@RequestBody loginRequest request) {
        try {
            Student student = studentservice.getStudent(request.emailId());
            Admin admin = null;
            if (student == null) {
                admin = adminservice.getAdmin(request.emailId());
            }
            if (student == null && admin == null) {
                throw new Exception("Invalid Login Credientials");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.emailId(), request.password()));
            if (!authentication.isAuthenticated()) {
                throw new Exception("Invalid Login Credentials");
            }
            String token = null;
            if (student != null) {
                token = jwtservice.generateToken(request.emailId(), "ROLE_USER");
            } else {
                token = jwtservice.generateToken(request.emailId(), "ROLE_ADMIN");
            }
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    record loginRequest(String emailId, String password) {
    }

    @PostMapping("/logout")
    public ResponseEntity<String> authLogout(){
        try{
            SecurityContextHolder.clearContext();
            return new ResponseEntity<String>("Logout Sucessfully", HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
