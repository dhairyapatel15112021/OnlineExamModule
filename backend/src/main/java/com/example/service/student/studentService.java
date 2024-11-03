package com.example.service.student;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Student;
import com.example.repository.student.StudentRepository;

@Service
public class studentService {
    
    private StudentRepository studentRepo;

    public studentService(StudentRepository studentRepo) {
        this.studentRepo = studentRepo;
    }
    
    public Student getStudent(String email){
        return studentRepo.findByEmailId(email);
    }
    
    public Boolean createAdmin(Student student){
        try{
            studentRepo.save(student);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    
    public List<Student> getStudents(){
        return studentRepo.findAll();
    }
}
