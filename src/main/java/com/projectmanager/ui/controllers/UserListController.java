package com.projectmanager.ui.controllers;

import com.projectmanager.models.entity.User;
import com.projectmanager.repository.UserRepository;
import com.projectmanager.session.UserSession;
import com.projectmanager.ui.SceneSwitcher;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class UserListController implements Initializable {

    @FXML private TableView<User>            tableView;
    @FXML private TableColumn<User, Integer> colId;
    @FXML private TableColumn<User, String>  colUsername;
    @FXML private TableColumn<User, String>  colEmail;
    @FXML private TableColumn<User, String>  colRole;
    @FXML private TableColumn<User, String>  colStatus;
    @FXML private Label                      lblMessage;

    private final UserRepository userRepo = new UserRepository();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Guard: neu khong phai admin -> quay ve dashboard ngay
        if (!UserSession.isAdmin()) {
            try { SceneSwitcher.switchScene("dashboard.fxml"); }
            catch (IOException e) { e.printStackTrace(); }
            return;
        }

        colId.setCellValueFactory(d ->
            new SimpleIntegerProperty(d.getValue().id).asObject());
        colUsername.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().username));
        colEmail.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().email));

        // Cot Role: admin=xanh duong, user=xam
        colRole.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().role));
        colRole.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item.toUpperCase());
                setStyle("admin".equals(item)
                    ? "-fx-text-fill: #89B4FA; -fx-font-weight: bold;"
                    : "-fx-text-fill: #6C7086;");
            }
        });

        // Cot Status: Active=xanh la, Blocked=do
        colStatus.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().status ? "Active" : "Blocked"));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                setStyle("Active".equals(item)
                    ? "-fx-text-fill: #A6E3A1; -fx-font-weight: bold;"
                    : "-fx-text-fill: #F38BA8; -fx-font-weight: bold;");
            }
        });

        loadData();
        lblMessage.setText("");
    }

    private void loadData() {
        tableView.setItems(FXCollections.observableArrayList(userRepo.findAll()));
    }

    @FXML
    private void handleToggleStatus() {
        User selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) { showMsg("Vui long chon 1 user.", false); return; }

        // Khong cho admin tu block chinh minh
        if (selected.id == UserSession.get().id) {
            showMsg("Khong the khoa chinh tai khoan cua ban.", false);
            return;
        }

        boolean newStatus = !selected.status;
        String  action    = newStatus ? "mo khoa" : "khoa";

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Ban co muon " + action + " user \"" + selected.username + "\"?",
            ButtonType.YES, ButtonType.NO);
        alert.setTitle("Xac nhan");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                if (userRepo.updateStatus(selected.id, newStatus)) {
                    loadData();
                    showMsg("Da " + action + " user: " + selected.username, true);
                } else {
                    showMsg("Thao tac that bai.", false);
                }
            }
        });
    }

    @FXML
    private void handleRefresh() {
        loadData();
        showMsg("Da tai lai danh sach.", true);
    }

    private void showMsg(String msg, boolean success) {
        lblMessage.setStyle("-fx-text-fill: " + (success ? "#A6E3A1" : "#F38BA8") + ";"
            + "-fx-font-size: 12px; -fx-font-style: italic;");
        lblMessage.setText(msg);
    }

    @FXML
    private void goBack() throws IOException { SceneSwitcher.switchScene("dashboard.fxml"); }
}
