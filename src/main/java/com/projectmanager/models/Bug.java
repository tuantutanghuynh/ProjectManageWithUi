/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.models;

/**
 *
 * @author tangh
 */
public class Bug extends Task implements ISeverityRatable {
    
    public String severity;
    
    @Override
    public int GetEffort() {
       return getSeverityScore()*3;
    }

    @Override
    public String GetStatusLabel() {
      return switch (status == null ? "" : status){
          case "todo" -> "[BUG] Pending ";
          case "in_progress" -> "[BUG] In progress";
          case "done" ->"[BUG] fixed";
          default -> "[BUG] Undefined";
      };
    }

    @Override
    public String getTypeCode() {
        return "B";
    }

    @Override
    public String getSeverityLabel() {
        return switch (severity == null ? "" : severity){
            case "LOW" ->  "Low";
            case "MEDIUM" -> "Medium";
            case "HIGH" -> "High";
            case "CRITICAL" -> "Crititcal";
            default -> "Undefined";
        };
    }

    @Override
    public int getSeverityScore() {
        return switch (severity == null? "" :severity){
            case "LOW" ->  1;
            case "MEDIUM" -> 2;
            case "HIGH" -> 3;
            case "CRITICAL" -> 4;
            default -> 0;
        };
    }
    
}
