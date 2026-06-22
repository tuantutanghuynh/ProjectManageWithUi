/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.models;

//feature implement

public interface IAssignable {
    
    void assign(String developerName);
    
    String getAssignedTo();
    
    boolean isAssigned();
}
