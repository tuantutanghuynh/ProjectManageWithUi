package com.projectmanager.utils;

import java.security.MessageDigest;
import java.util.Base64;

import com.projectmanager.exceptions.AppException;

public class PasswordHasher {
    
    private PasswordHasher() {}

    public static String hash(String password){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(digest);
        }catch (Exception e){
            throw new AppException("Hash error: " + e.getMessage(), e);
        }
    }

    public static boolean verify(String plainPassword, String storedHash){
        return hash(plainPassword).equals(storedHash);
    }
}
