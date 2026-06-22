/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.models;

/**
 *
 * @author tangh
 */
public class Feature extends Task implements IAssignable {
    
    public int estimatedHours;
    private String assignedTo = "";

    @Override
    public int GetEffort() {
        return estimatedHours;
    }

    @Override
    public String GetStatusLabel() {
       return switch (status == null ? "" : status){
           case "todo" -> "[FEAT] Not started";
           case "in_progress" -> "[FEAT] In progress";
           case "done" -> "[FEAT] Done";
           default -> "[FEAT] Undefined";
       };
    }

    @Override
    public String getTypeCode() {
        return "F";
    }

    @Override
    public void assign(String developerName) {
        this.assignedTo = (developerName == null) ? " " : developerName.trim();
    }

    @Override
    public String getAssignedTo() {
        return assignedTo;
    }

    @Override
    public boolean isAssigned() {
        return assignedTo != null&& !assignedTo.isBlank();
    }
    
}
