package com.example.service.admin;

import org.springframework.stereotype.Service;
import com.example.model.Admin;
import com.example.repository.admin.AdminRepository;

@Service
public class adminService {
    
    private AdminRepository adminRepo;

    public adminService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }
    
    public Admin getAdmin(String email){
        return adminRepo.findByEmailId(email);
    }

    public Boolean createAdmin(Admin admin){
        try{
            adminRepo.save(admin);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
}
