package com.projectmanager.ui.controllers;

import com.projectmanager.models.Task;
import com.projectmanager.service.ProjectService;
import com.projectmanager.session.UserSession;
import com.projectmanager.ui.SceneSwitcher;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class DashboardController implements Initializable {

    @FXML private Label  lblWelcome;
    @FXML private Label  lblRole;
    @FXML private Label  lblStats;
    @FXML private Button btnManageUsers;   // chi hien voi admin

    private final ProjectService<Task> service = ProjectService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lblWelcome.setText("Xin chao, " + UserSession.get().username);
        lblRole.setText("Role: " + UserSession.get().role.toUpperCase());
        lblStats.setText("Dang tai du lieu...");

        // An "Manage Users" voi user thuong - chi admin thay
        btnManageUsers.setVisible(UserSession.isAdmin());
        btnManageUsers.setManaged(UserSession.isAdmin());

        // Load DB async - callback cap nhat stats khi xong
        service.loadFromDBAsync(() -> {
            Map<String, Integer> byStatus = service.countByStatus();
            lblStats.setText(String.format(
                "Tong: %d task  |  Bug: %d  Feature: %d  |  Effort: %dh%n"
                + "Todo: %d  |  Dang lam: %d  |  Xong: %d",
                service.getSize(), service.countBugs(), service.countFeatures(), service.totalEffort(),
                byStatus.getOrDefault("todo", 0),
                byStatus.getOrDefault("in_progress", 0),
                byStatus.getOrDefault("done", 0)
            ));
        });
    }

    @FXML private void goAddTask()  throws IOException { SceneSwitcher.switchScene("add_task.fxml"); }
    @FXML private void goTaskList() throws IOException { SceneSwitcher.switchScene("task_list.fxml"); }

    @FXML
    private void goManageUsers() throws IOException {
        if (!UserSession.isAdmin()) return;   // double-check du button da bi an
        SceneSwitcher.switchScene("user_list.fxml");
    }

    @FXML
    private void handleLogout() throws IOException {
        UserSession.clear();
        ProjectService.reset();   // xoa cache - tranh data cua user nay ro sang user tiep theo
        SceneSwitcher.switchScene("login.fxml");
    }
}
