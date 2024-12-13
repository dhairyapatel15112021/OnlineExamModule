package com.example.service.college;

import org.springframework.stereotype.Service;

import com.example.model.College;
import com.example.repository.college.collegeRepository;
import com.example.service.admin.adminService;
import com.example.service.student.studentService;

@Service
public class collegeService {
    private final collegeRepository collegeRepository;
    private final studentService studentService;
    private final adminService adminService;


    public collegeService(collegeRepository collegeRepository,studentService studentService,adminService adminService) {
        this.collegeRepository = collegeRepository;
        this.studentService = studentService;
        this.adminService = adminService;
    }

    public College getCollege(String emailId){
        return collegeRepository.findByEmailId(emailId);
    }

    public boolean createCollege(College clg){
        try{
            if(studentService.getStudent(clg.getEmailId()) != null || adminService.getAdmin(clg.getEmailId()) != null || getCollege(clg.getEmailId()) != null){
                throw new Exception("Invalid Credentials");
            }
            if(clg.getContactNumber().length() != 10){
                throw new Exception("Invalid Contact Number, length Should be Length of 10 digit");
            }
            collegeRepository.save(clg);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
