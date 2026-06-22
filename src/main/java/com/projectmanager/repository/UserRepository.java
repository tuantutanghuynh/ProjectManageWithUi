/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.repository;
import com.projectmanager.config.DatabaseConfig;
import com.projectmanager.models.entity.User;
import java.sql.*;
import java.util.*;
/**
 *
 * @author tangh
 */
public class UserRepository {
     private final Connection conn = DatabaseConfig.getConnection();
     
     //------------Find by user-------------for login
   public User findByUsername(String username) {
    String sql = "SELECT * FROM Users WHERE Username = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return mapRow(rs);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}
    
    public boolean existByUsername(String username){
        return findByUsername(username) != null;
    }
    
    //--------Get all users - admin only (user list Controller)
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY Role DESC, Username";
        try (Statement st =conn.createStatement()){
            ResultSet rs =  st.executeQuery(sql);
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return list;
    }
    
    //-----------INSERT --- for registration
    public boolean insert(User u){
        String sql = "INSERT INTO Users(Username, PasswordHash, Email, Role, Status) VALUES(?, ?, ?, ?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, u.username);
            ps.setString(2, u.passwordHash);
            ps.setString(3, u.email == null ? "" : u.email);
            ps.setString(4, u.role);
            ps.setBoolean(5, u.status);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("[REPO] Insert user error: " + e.getMessage());
            return false;
        }
    }
    
    //--------Block and unblock user - admin only
    public boolean updateStatus(int userId, boolean newStatus){
        String sql = "UPDATE Users SET Status = ? WHERE UserId = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setBoolean(1, newStatus);
            ps.setInt(2, userId);
            return ps.executeUpdate() >0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    //--------mapRow--------------------------------
    private User mapRow(ResultSet rs) throws SQLException{
        User u = new User();
        u.id = rs.getInt("UserId");
        u.username = rs.getString("Username");
        u.passwordHash = rs.getString("PasswordHash");
        u.email = rs.getString("Email");
        u.role = rs.getNString("Role");
        u.status = rs.getBoolean("Status");
        return u;
    }
}
