/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.models.dto;

/**
 *
 * @author tangh
 */
public class LoginRequest {
    public final String username;
    public final String password;
    
    public LoginRequest(String username, String password){
        this.username = username;
        this.password  =  password;
    }
}
