/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.config;

import java.sql.*;


/**
 *
 * @author tangh
 */
public class DatabaseConfig {
    private static Connection connection;

    private static final String URL      = "jdbc:sqlserver://localhost:1433;databaseName=ProjectManagerDB;encrypt=false";
    private static final String USER     = "sa";
    private static final String PASSWORD = "tuantu209423";

    private DatabaseConfig() {}

    public static synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (Exception e) {
            System.out.println("DB connection error: " + e.getMessage());
        }
        return connection;
    }
}
