package com.example.service.college;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.College;
import com.example.repository.college.CollegeRepository;
import com.example.service.admin.AdminService;
import com.example.service.student.StudentService;

@Service
public class CollegeService {
    private final CollegeRepository collegeRepository;
    private final StudentService studentService;
    private final AdminService adminService;


    public CollegeService(CollegeRepository collegeRepository,StudentService studentService,AdminService adminService) {
        this.collegeRepository = collegeRepository;
        this.studentService = studentService;
        this.adminService = adminService;
    }

    public College getCollege(String emailId){
        return collegeRepository.findByEmailId(emailId);
    }

    public College createCollege(College clg){
        try{
            if(studentService.getStudent(clg.getEmailId()) != null || adminService.getAdmin(clg.getEmailId()) != null || getCollege(clg.getEmailId()) != null){
                throw new Exception("Invalid Credentials");
            }
            if(clg.getContactNumber().length() != 10){
                throw new Exception("Invalid Contact Number, length Should be Length of 10 digit");
            }
            College college = collegeRepository.save(clg);
            return college;
        }
        catch(Exception e){
            return null;
        }
    }

    public List<College> getCollges() {
        try{
            return collegeRepository.findAll();
        }
        catch(Exception e){
            return List.of();
        }
    }
}
