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
        if( u == null){
            return null;
        }
        if(!PasswordHasher.verify(req.password, u.passwordHash)){
            return null;
        }
        return u;
    }

    //register: trả về user nếu đăng ký thành công, trả về null nếu thất bại
    public boolean register()
}
