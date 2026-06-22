/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.models;

/**
 *
 * @author tangh
 */
public abstract class Task implements ITask, IPersistable {
    public String id;
    public String title;
    public String priority;
    public String status;
    
    //effort with different way of calculation: Bug=severityx3H, Feature= estimatedHours
    
    public abstract int GetEffort();
    
    public final void printSummary() {
        System.out.printf("[%-1s] %-8s | %-35s | Pri:%-6s | %-22s | %dh%n",
                getTypeCode(), id, title, priority, GetStatusLabel(), GetEffort());
    }
    
}
