/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.factory;

import com.projectmanager.models.*;


/**
 *
 * @author tangh
 */
public class TaskFactory {
    
    private TaskFactory(){};
    
    public static Task create(String typeCode){
        return switch(typeCode.toUpperCase()){
            case "B" -> new Bug();
            case "F" -> new Feature();
            default -> throw new IllegalArgumentException(
                "Loai task khong hop le: \"" + typeCode+"\". Chi chap nhan B hoac F");
        };
                }
    
}
