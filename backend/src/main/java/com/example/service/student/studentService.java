package com.example.service.student;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Batch;
import com.example.model.College;
import com.example.model.Student;
import com.example.repository.batch.batchRepository;
import com.example.repository.college.collegeRepository;
import com.example.repository.student.StudentRepository;

@Service
public class studentService {
    
    private final StudentRepository studentRepo;
    private final collegeRepository collegeRepository;
    private final batchRepository batchRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public studentService(StudentRepository studentRepo,collegeRepository collegeRepository,batchRepository batchRepository) {
        this.studentRepo = studentRepo;
        this.batchRepository = batchRepository;
        this.collegeRepository = collegeRepository;
    }
    
    public Student getStudent(String email){
        return studentRepo.findByEmailId(email);
    }
    
    public Boolean createStudent(Student student ,int batchId , int clgId){
        try{
            College clg = collegeRepository.getReferenceById(clgId);
            Batch batch = batchRepository.getReferenceById(batchId);
            student.setBatch(batch);
            student.setClg(clg);
            student.setPassword(encoder.encode(student.getPassword()));
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
