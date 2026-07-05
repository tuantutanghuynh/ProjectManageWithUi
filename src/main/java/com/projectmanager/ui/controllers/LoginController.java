package com.projectmanager.ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.projectmanager.models.dto.LoginRequest;
import com.projectmanager.models.entity.User;
import com.projectmanager.service.AuthService;
import com.projectmanager.session.UserSession;
import com.projectmanager.ui.SceneSwitcher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class LoginController implements Initializable {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMessage;

    private final AuthService authService = new AuthService();

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        lblMessage.setText("");
    }

    @FXML
    private void handleLogin() throws IOException {
        try{
            User u = authService.login(new LoginRequest(
                txtUsername.getText().trim(),
                txtPassword.getText()
            ));

            if (u == null) {
                showMsg("Sai username hoac mat khau.", false); 
                return;
            }

            if (!u.status){
                showMsg("Tai khoan bị khoa. Lien he admin.", false);
                return;
            }

            UserSession.set(u);
            SceneSwitcher.switchScene("dashboard.fxml");
        } catch (IllegalArgumentException e) {
            showMsg(e.getMessage(), false);
        } catch (Exception e) {
            showMsg("Loi he thong: " + e.getMessage(), false);
        }
    }

    @FXML
    private void goRegister() throws IOException {
        SceneSwitcher.switchScene("register.fxml");
    }

    private void showMsg(String msg, boolean success){
        lblMessage.setStyle("-fx-text-fill: " + (success ? "#A6E3A1" : "#F38BA8") + ";"
            + "-fx-font-size: 12px; -fx-font-style: italic;");
        lblMessage.setText(msg);
    }

}
