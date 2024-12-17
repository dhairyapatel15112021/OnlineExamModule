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
import com.example.service.admin.AdminService;
import com.example.service.college.CollegeService;
import com.example.service.jwt.JwtService;
import com.example.service.student.StudentService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AdminService adminService;
    private final StudentService studentservice;
    private final CollegeService collegeService;
    private final JwtService jwtservice;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AdminService adminservice, StudentService studentservice, JwtService jwtservice,CollegeService collegeService,
            AuthenticationManager authenticationManager) {
        this.adminService = adminservice;
        this.studentservice = studentservice;
        this.jwtservice = jwtservice;
        this.authenticationManager = authenticationManager;
        this.collegeService = collegeService;
    }

    // @PostMapping("/admin")
    public Admin admin(@RequestBody adminRequest request){
        return adminService.getAdmin(request.emailId());
    }

    record adminRequest(String emailId){}
    
    @PostMapping("/admin/register")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin admin){
        try{
            if(studentservice.getStudent(admin.getEmailId()) != null || adminService.getAdmin(admin.getEmailId()) != null || collegeService.getCollege(admin.getEmailId()) != null){
                throw new Exception("Exist with this email id");
            }
            Boolean response = adminService.createAdmin(admin);
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
                admin = adminService.getAdmin(request.emailId());
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
                token = jwtservice.generateToken(request.emailId(), "ROLE_USER" , student.getId());
            } else {
                token = jwtservice.generateToken(request.emailId(), "ROLE_ADMIN" , admin.getId());
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
