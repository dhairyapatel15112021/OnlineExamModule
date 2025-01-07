package com.example.service.admin;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.model.Admin;
import com.example.repository.admin.AdminRepository;

@Service
public class AdminService {
    
    private AdminRepository adminRepo;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AdminService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }
    
    public Admin getAdmin(String email){
        return adminRepo.findByEmailId(email);
    }

    public Boolean createAdmin(Admin admin){
        try{
            admin.setPassword(encoder.encode(admin.getPassword()));
            adminRepo.save(admin);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
