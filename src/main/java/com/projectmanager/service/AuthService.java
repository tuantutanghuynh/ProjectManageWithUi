package com.projectmanager.service;

import com.projectmanager.models.dto.LoginRequest;
import com.projectmanager.models.entity.User;
import com.projectmanager.repository.UserRepository;
import com.projectmanager.utils.PasswordHasher;
import com.projectmanager.utils.Validator;

public class AuthService {
    
    private final UserRepository userRepo = new UserRepository();

    //login
    public User login(LoginRequest req) {
        Validator.requireNonBlank(req.username, "Username");
        Validator.requireNonBlank(req.password, "Mat khau");

        User u = userRepo.findByUsername(req.username);
        if (u == null) return null;
        if (!PasswordHasher.verify(req.password, u.passwordHash)) return null;
        return u;
    }

    //register: trả về user nếu đăng ký thành công, trả về null nếu thất bại
    public boolean register(String username, String password, String confirmPassword, String email, String role){
        
        Validator.requireNonBlank(username, "Username");
        Validator.requireMinLength(username, "Username",3);
        Validator.requireNonBlank(password, "Mat khau");
        Validator.requireMinLength(password, "Mat khau", 6);

        if(!password.equals(confirmPassword)){
            throw new IllegalArgumentException("Mat khau xac nhan khong khop. ");
        }

        if(userRepo.existByUsername(username)){
            throw new IllegalArgumentException("Username \"" + username + "\" da ton tai. ");
        }

        User u = new User();
        u.username = username;
        u.passwordHash = PasswordHasher.hash(password);
        u.email = (email == null) ? "" : email.trim();
        u.role = (role == null || role.isBlank())? "user" : role;
        u.status = true;

        return userRepo.insert(u);
    }
}
