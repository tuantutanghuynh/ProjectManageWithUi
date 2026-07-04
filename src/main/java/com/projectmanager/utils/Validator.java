package com.projectmanager.utils;

public class Validator {
    
    private Validator(){}

    public static void requireNonBlank(String value, String fieldName){
        if(value == null || value.isBlank()){
             throw new IllegalArgumentException(fieldName + " Khong dược de trong");
        }       
    }

    public static void requireMinLength(String value, String fieldName, int min){
        if(value != null && value.length() < min){
            throw new IllegalArgumentException(fieldName + " phai co it nhat " + min + "ky tu")
;        }
    }
    }
