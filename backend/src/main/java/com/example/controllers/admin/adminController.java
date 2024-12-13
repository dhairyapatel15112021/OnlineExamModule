package com.example.controllers.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import com.example.model.Admin;
import com.example.model.Batch;
import com.example.model.College;
import com.example.model.Student;
import com.example.service.admin.adminService;
import com.example.service.batch.batchService;
import com.example.service.college.collegeService;
import com.example.service.student.studentService;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/api/v1/admin")
public class adminController {
    
    private final adminService adminservice;
    private final studentService studentservice;
    private final collegeService collegeService;
    private final batchService batchService;

    public adminController(adminService adminservice , studentService studentservice,collegeService collegeService,batchService batchService) {
        this.adminservice = adminservice;
        this.studentservice = studentservice;
        this.collegeService = collegeService;
        this.batchService = batchService;
    }

    @GetMapping("/students")
    public List<Student> students(){
        return studentservice.getStudents();
    }

    // @PostMapping("/student")
    public Student student(@RequestBody studentRequest request){
        return studentservice.getStudent(request.emailId());
    }

    record studentRequest(String emailId){}

    @PostMapping("/student/register")
    public ResponseEntity<studentRegisterResponse> createStudent(@RequestBody List<studentRegisterRequest> studentRegisterRequest){
        ArrayList<studentRegisterRequest> responseUnsuceesfull  = new ArrayList<studentRegisterRequest>();
        ArrayList<studentRegisterRequest> responseSucessfull = new ArrayList<studentRegisterRequest>();
        studentRegisterResponse studentRegisterResponse = new studentRegisterResponse(responseSucessfull,responseUnsuceesfull);
  
            for(studentRegisterRequest stu : studentRegisterRequest){
                try{
                    if(studentservice.getStudent(stu.emailId()) != null || adminservice.getAdmin(stu.emailId()) != null || collegeService.getCollege(stu.emailId()) != null){
                        throw new Exception("Exist with this email id");
                    }
                    if(stu.mobileNumber().length() != 10){
                        throw new Exception("Mobile Number Length should be 10");
                    }
                    Student student = new Student();
                    student.setEmailId(stu.emailId());
                    student.setPassword(stu.password());
                    student.setName(stu.name());
                    student.setEnrollmentNumber(stu.enrollmentNumber());
                    student.setMobileNumber(stu.mobileNumber());
                   Boolean responseRegisterStudent = studentservice.createStudent(student, stu.batchId() , stu.clgId());
                    if(!responseRegisterStudent){
                        throw new Exception("Problem While saving the Student");
                    }
                    responseSucessfull.add(stu);
                }
                catch(Exception e){
                    responseUnsuceesfull.add(stu);
                }
            }
         
        return ResponseEntity.status(responseUnsuceesfull.size() == 0 ? HttpStatus.OK : HttpStatus.MULTI_STATUS)
                .body(studentRegisterResponse);
            
    }

    record studentRegisterRequest(String emailId,String password , String name , String enrollmentNumber , String mobileNumber , int batchId , int clgId) {}
    record studentRegisterResponse(ArrayList<studentRegisterRequest> success , ArrayList<studentRegisterRequest> failure){};

    @PostMapping("/clg/register")
    public ResponseEntity<String> createCollege(@RequestBody College clg){
        try{
            if(collegeService.createCollege(clg)){
                return new ResponseEntity<String>("College Created Succesfully", HttpStatus.OK);
            }
            throw new Exception("Something Went Wrong");
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/batch/create")
    public ResponseEntity<String> createBatch(@RequestBody Batch batch){
        try{
            if(batchService.createBatch(batch)){
                return new ResponseEntity<String>("Batch Created Succesfully", HttpStatus.OK);
            }
            throw new Exception("Something Went Wrong");
        }
        catch(Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
