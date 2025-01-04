package com.example.service.student;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.model.Batch;
import com.example.model.College;
import com.example.model.Student;
import com.example.repository.batch.BatchRepository;
import com.example.repository.college.CollegeRepository;
import com.example.repository.student.StudentRepository;

@Service
public class StudentService {
    
    private final StudentRepository studentRepo;
    private final CollegeRepository collegeRepository;
    private final BatchRepository batchRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

  public StudentService(StudentRepository studentRepo, CollegeRepository collegeRepository, BatchRepository batchRepository) {
        this.studentRepo = studentRepo;
        this.collegeRepository = collegeRepository;
        this.batchRepository = batchRepository;
    }

    
    
    public Student getStudent(String email){
        return studentRepo.findByEmailId(email);
    }

    public Student getStudent(int id){
        return studentRepo.getReferenceById(id);
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
