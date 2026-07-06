package com.projectmanager.ui.controllers;

import com.projectmanager.factory.TaskFactory;
import com.projectmanager.models.*;
import com.projectmanager.service.ProjectService;
import com.projectmanager.ui.SceneSwitcher;
import com.projectmanager.utils.Validator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class AddTaskController implements Initializable {

    @FXML private TextField        txtId;
    @FXML private TextField        txtTitle;
    @FXML private ComboBox<String> cbPriority;
    @FXML private ComboBox<String> cbStatus;
    @FXML private ToggleButton     btnBug;
    @FXML private ToggleButton     btnFeature;
    // Bug fields
    @FXML private HBox             rowSeverity;
    @FXML private ComboBox<String> cbSeverity;
    // Feature fields
    @FXML private HBox             rowHours;
    @FXML private HBox             rowDeveloper;
    @FXML private TextField        txtHours;
    @FXML private TextField        txtDeveloper;
    @FXML private Label            lblMessage;

    private final ToggleGroup       typeGroup = new ToggleGroup();
    private final ProjectService<Task> service = ProjectService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPriority.getItems().addAll("HIGH", "MEDIUM", "LOW");
        cbPriority.getSelectionModel().selectFirst();

        cbStatus.getItems().addAll("todo", "in_progress", "done");
        cbStatus.getSelectionModel().selectFirst();

        cbSeverity.getItems().addAll("LOW", "MEDIUM", "HIGH", "CRITICAL");
        cbSeverity.getSelectionModel().selectFirst();

        btnBug.setToggleGroup(typeGroup);
        btnFeature.setToggleGroup(typeGroup);
        btnBug.setSelected(true);   // Bug mac dinh

        setFeatureFieldsVisible(false);

        // Listener: doi loai task -> an/hien fields tuong ung
        typeGroup.selectedToggleProperty().addListener((obs, old, val) -> {
            boolean isBug = (val == btnBug);
            setFeatureFieldsVisible(!isBug);
            setVisible(rowSeverity, isBug);
        });

        lblMessage.setText("");
    }

    @FXML
    private void handleAdd() {
        try {
            Validator.requireNonBlank(txtId.getText(),    "Task ID");
            Validator.requireNonBlank(txtTitle.getText(), "Tieu de");

            boolean isBug   = btnBug.isSelected();
            Task    task    = TaskFactory.create(isBug ? "B" : "F");
            task.id       = txtId.getText().trim().toUpperCase();
            task.title    = txtTitle.getText().trim();
            task.priority = cbPriority.getValue();
            task.status   = cbStatus.getValue();

            if (isBug) {
                ((Bug) task).severity = cbSeverity.getValue();
            } else {
                Feature feat = (Feature) task;
                feat.estimatedHours = Validator.parsePositiveInt(txtHours.getText(), "So gio");
                String dev = txtDeveloper.getText().trim();
                feat.assign(dev.isEmpty() ? null : dev);
            }

            if (service.Add(task)) {
                showMsg("Da them " + (isBug ? "Bug" : "Feature") + ": " + task.id, true);
                clearForm();
            } else {
                showMsg("Them task that bai. ID co the da ton tai.", false);
            }
        } catch (IllegalArgumentException e) {
            showMsg(e.getMessage(), false);
        }
    }

    private void setFeatureFieldsVisible(boolean visible) {
        setVisible(rowHours,     visible);
        setVisible(rowDeveloper, visible);
    }

    // setManaged(false) khi an - tranh row chiem cho layout khi invisible
    private void setVisible(HBox row, boolean visible) {
        row.setVisible(visible);
        row.setManaged(visible);
    }

    private void clearForm() {
        txtId.clear(); txtTitle.clear(); txtHours.clear(); txtDeveloper.clear();
        cbPriority.getSelectionModel().selectFirst();
        cbStatus.getSelectionModel().selectFirst();
        cbSeverity.getSelectionModel().selectFirst();
        btnBug.setSelected(true);
    }

    private void showMsg(String msg, boolean success) {
        lblMessage.setStyle("-fx-text-fill: " + (success ? "#A6E3A1" : "#F38BA8") + ";"
            + "-fx-font-size: 12px; -fx-font-style: italic;");
        lblMessage.setText(msg);
    }

    @FXML
    private void goBack() throws IOException { SceneSwitcher.switchScene("dashboard.fxml"); }
}
