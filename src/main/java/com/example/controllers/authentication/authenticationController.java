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

    public AuthenticationController(AdminService adminservice, StudentService studentservice, JwtService jwtservice,
            CollegeService collegeService,
            AuthenticationManager authenticationManager) {
        this.adminService = adminservice;
        this.studentservice = studentservice;
        this.jwtservice = jwtservice;
        this.authenticationManager = authenticationManager;
        this.collegeService = collegeService;
    }

    // @PostMapping("/admin")
    public Admin admin(@RequestBody adminRequest request) {
        return adminService.getAdmin(request.emailId());
    }

    record adminRequest(String emailId) {
    }

    @PostMapping("/admin/register")
    public ResponseEntity<adminRegistrationResponse> registerAdmin(@RequestBody Admin admin) {
        try {
            if (studentservice.getStudent(admin.getEmailId()) != null
                    || adminService.getAdmin(admin.getEmailId()) != null
                    || collegeService.getCollege(admin.getEmailId()) != null) {
                throw new Exception("Exist with this email id");
            }
            Boolean response = adminService.createAdmin(admin);
            if (!response) {
                throw new Exception("Problem While Saving the admin");
            }
            return new ResponseEntity<>(new adminRegistrationResponse("Registered"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    record adminRegistrationResponse(String message) {}

    @PostMapping("/login")
    public ResponseEntity<loginResponse> authLogin(@RequestBody loginRequest request) {

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

            if (student != null) {
                String token = jwtservice.generateToken(request.emailId(), "ROLE_USER", student.getId());
                loginResponse loginResponse = new loginResponse(token, false);
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
                // we have to do like this because of final keyword while declaring variable in
                // the record.
            } else {
                String token = jwtservice.generateToken(request.emailId(), "ROLE_ADMIN", admin.getId());
                loginResponse loginResponse = new loginResponse(token, true);
                return new ResponseEntity<>(loginResponse, HttpStatus.OK);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    record loginRequest(String emailId, String password) {
    }

    record loginResponse(String token, boolean isAdmin) {
    }

    @PostMapping("/logout")
    public ResponseEntity<logoutResponse> authLogout() {
        try {
            SecurityContextHolder.clearContext();
            return new ResponseEntity<>(new logoutResponse("Succefully logout"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new logoutResponse("ERROR"), HttpStatus.BAD_REQUEST);
        }
    }
    record logoutResponse(String message){}
}
