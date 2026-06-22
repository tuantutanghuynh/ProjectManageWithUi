/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.service;

import com.projectmanager.models.IProjectAnalytics;
import com.projectmanager.models.Task;
import com.projectmanager.repository.TaskRepository;
import java.util.HashMap;
import java.util.*;
import java.util.Map;
import javafx.application.Platform;

/**
 *
 * @author tangh
 */
public class ProjectService<T extends Task> implements IProjectAnalytics {
    
    //---Singleton
    private static ProjectService<Task> instance;
    
    public static ProjectService<Task> getInstance() {
        if(instance == null) instance = new ProjectService<>();
        return instance;
    }
    
    public static void reset(){
        instance = null;
    }
    
   private final Map<String, T> map = new HashMap<>();
   private final List<T> list = new ArrayList<>();
   private final Object lock = new Object();
   private final TaskRepository  repo= new TaskRepository();
   
   //Load from DB
   @SuppressWarnings("unchecked")
   public void loadFromDB(){
       List<Task> dbList = repo.findAll();
       
       //build into temp collections then swap into short lock
       Map<String, T> tempMap = new HashMap<>();
       List<T> tempList = new ArrayList<>();
       for(Task t : dbList){
           T value = (T) t;
           if(!tempMap.containsKey(value.id)){
               tempMap.put(value.id, value);
               tempList.add(value);
           }
       }
       synchronized (lock) {
           map.clear();
           list.clear();
           map.putAll(tempMap);
           list.addAll(tempList);
       }
   }
   

     public Thread loadFromDBAsync(Runnable onComplete){
    Thread worker = new Thread(() -> {
        loadFromDB();

        if (onComplete != null) {
             Platform.runLater(onComplete);
        }
    }, "load-db-thread");

    worker.setDaemon(true);
    worker.start();
    return worker;
}


    @Override
    public Map<String, Integer> countByPriority() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<String, Integer> countByStatus() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void showSummary() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
