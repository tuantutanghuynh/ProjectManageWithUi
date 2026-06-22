/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.models;

import java.util.Map;

/**
 *
 * @author tangh
 */
public interface IProjectAnalytics {
    //{"LOW" ->2, "MEDIUM"->3, "HIGH"->1}
    Map<String, Integer> countByPriority();
    
    //{"todo" ->3, "in_progress" ->1, "done" ->2}
    Map<String, Integer> countByStatus();
    
    //print sum to the console
    void showSummary();
    
}
