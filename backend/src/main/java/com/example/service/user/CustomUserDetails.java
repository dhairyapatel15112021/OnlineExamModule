package com.example.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.model.Admin;
import com.example.model.CustomAdmin;
import com.example.model.CustomStudent;
import com.example.model.Student;
import com.example.repository.admin.AdminRepository;
import com.example.repository.student.StudentRepository;

@Service
public class CustomUserDetails implements UserDetailsService {

    private final StudentRepository studentRepository;
    private final AdminRepository adminRepository;

    
    public CustomUserDetails(StudentRepository studentRepository, AdminRepository adminRepository) {
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByEmailId(username);
        Admin admin = null;
        if (student == null){
            admin = adminRepository.findByEmailId(username);
        }
        else{
            return new CustomStudent(student);
        }
        if (admin == null){
            throw new UsernameNotFoundException("Invalid Not Found");
        }
        return new CustomAdmin(admin);
    }
    
}
