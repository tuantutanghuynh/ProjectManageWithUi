/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projectmanager.repository;

import com.projectmanager.config.DatabaseConfig;
import com.projectmanager.models.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author tangh
 */
public class TaskRepository {

    private final Connection conn = DatabaseConfig.getConnection();

    //----------INSERT---------------------------------------------
    public boolean insert(Task t) {
        String sql = "INSERT INTO Tasks"
                + "(TaskId, Title, Priority, Status, TaskType, Severity, EstimatedHours, AssignedTo)"
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.id);
            ps.setString(2, t.title);
            ps.setString(3, t.priority);
            ps.setString(4, t.status);
            ps.setString(5, t.getTypeCode());
            if (t instanceof Bug) {
                Bug b = (Bug) t;
                ps.setString(6, b.severity);
                ps.setNull(7, Types.INTEGER);
                ps.setNull(8, Types.NVARCHAR);
            } else {
                Feature f = (Feature) t;
                ps.setNull(6, Types.VARCHAR);
                ps.setInt(7, f.estimatedHours);
                if (f.isAssigned()) {
                    ps.setString(8, f.getAssignedTo());
                } else {
                    ps.setNull(8, Types.NVARCHAR);
                }
            }
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("[REPO] Inset error: " + e.getMessage());
            return false;
        }
    }

    //----SELECT ALL-------------------------------------
    public List<Task> findAll() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM Tasks ORDER BY "
                + "CASE Priority WHEN 'HIGH' THEN 1 WHEN 'MEDIUM' THEN 2 ELSE 3 END, TaskId";
        try (Statement st = conn.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //------SELECT BY ID------
    public Task findById(String id) {
        String sql = "SELECT * FROM Tasks WHERE TaskId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //-----UPDATE STATUS----------------
    public boolean updateStatus(String id, String newStatus) {
        String sql = "UPDATE Tasks SET Status = ? WHERE TaskId = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //-------------DELETE----------------
    public boolean delete(String id) {
        String sql = "DELETE Tasks Where TaskId= ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //---------mapRow
    private Task mapRow(ResultSet rs) throws SQLException {
        String type = rs.getString("TaskType");
        if ("B".equals(type)) {
            Bug b = new Bug();
            b.id = rs.getString("TaskId");
            b.title = rs.getString("Title");
            b.priority = rs.getString("Priority");
            b.status = rs.getString("Status");
            b.severity = rs.getString("Severity");
            return b;
        } else {
            Feature f = new Feature();
            f.id = rs.getString("TaskId");
            f.title = rs.getString("Title");
            f.priority = rs.getString("Priority");
            f.status = rs.getString("Status");
            f.estimatedHours = rs.getInt("EstimatedHours");
            f.assign(rs.getString("AssignedTo"));
            return f;
        }
    }
}
