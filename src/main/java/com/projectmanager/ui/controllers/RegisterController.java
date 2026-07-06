package com.projectmanager.ui.controllers;

import com.projectmanager.service.AuthService;
import com.projectmanager.ui.SceneSwitcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class RegisterController implements Initializable {

    @FXML private TextField        txtUsername;
    @FXML private PasswordField    txtPassword;
    @FXML private PasswordField    txtConfirm;
    @FXML private TextField        txtEmail;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label            lblMessage;

    private final AuthService authService = new AuthService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbRole.getItems().addAll("user", "admin");
        cbRole.getSelectionModel().selectFirst();   // "user" mac dinh
        lblMessage.setText("");
    }

    @FXML
    private void handleRegister() {
        try {
            boolean ok = authService.register(
                txtUsername.getText().trim(),
                txtPassword.getText(),
                txtConfirm.getText(),
                txtEmail.getText().trim(),
                cbRole.getValue()
            );
            if (ok) {
                showMsg("Tao tai khoan thanh cong. Vui long dang nhap.", true);
                clearForm();
            } else {
                showMsg("Dang ky that bai. Kiem tra ket noi DB.", false);
            }
        } catch (IllegalArgumentException e) {
            showMsg(e.getMessage(), false);
        }
    }

    @FXML
    private void goLogin() throws IOException {
        SceneSwitcher.switchScene("login.fxml");
    }

    private void clearForm() {
        txtUsername.clear(); txtPassword.clear();
        txtConfirm.clear();  txtEmail.clear();
        cbRole.getSelectionModel().selectFirst();
    }

    private void showMsg(String msg, boolean success) {
        lblMessage.setStyle("-fx-text-fill: " + (success ? "#A6E3A1" : "#F38BA8") + ";"
            + "-fx-font-size: 12px; -fx-font-style: italic;");
        lblMessage.setText(msg);
    }
}
