package com.projectmanager.ui.controllers;

import com.projectmanager.models.*;
import com.projectmanager.service.ProjectService;
import com.projectmanager.session.UserSession;
import com.projectmanager.ui.SceneSwitcher;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

public class TaskListController implements Initializable {

    @FXML private TableView<Task>            tableView;
    @FXML private TableColumn<Task, String>  colType;
    @FXML private TableColumn<Task, String>  colId;
    @FXML private TableColumn<Task, String>  colTitle;
    @FXML private TableColumn<Task, String>  colPriority;
    @FXML private TableColumn<Task, String>  colStatus;
    @FXML private TableColumn<Task, Integer> colEffort;
    @FXML private TableColumn<Task, String>  colExtra;
    @FXML private ComboBox<String>           cbFilterStatus;
    @FXML private ComboBox<String>           cbFilterType;
    @FXML private ComboBox<String>           cbNewStatus;
    @FXML private Button                     btnDelete;   // chi admin
    @FXML private Label                      lblStatus;
    @FXML private Label                      lblEffort;

    private final ProjectService<Task> service = ProjectService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupColumns();
        setupFilters();

        btnDelete.setVisible(UserSession.isAdmin());
        btnDelete.setManaged(UserSession.isAdmin());

        loadData(service.getAll());
    }

    private void setupColumns() {
        // Cot Type: mau do cho Bug, xanh teal cho Feature
        colType.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue() instanceof Bug ? "BUG" : "FEAT"));
        colType.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                setStyle("-fx-text-fill: " + ("BUG".equals(item) ? "#F38BA8" : "#89DCEB")
                    + "; -fx-font-weight: bold;");
            }
        });

        colId.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue().id));
        colTitle.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().title));

        // Cot Priority: HIGH=do, MEDIUM=cam, LOW=xanh la
        colPriority.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().priority));
        colPriority.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                String color = switch (item) {
                    case "HIGH"   -> "#F38BA8";
                    case "MEDIUM" -> "#FAB387";
                    default       -> "#A6E3A1";
                };
                setStyle("-fx-text-fill: " + color + "; -fx-font-weight: bold;");
            }
        });

        // Cot Status: todo=xam, in_progress=cam, done=xanh la
        colStatus.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().status));
        colStatus.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setText(null); setStyle(""); return; }
                setText(item);
                String color = switch (item) {
                    case "in_progress" -> "#FAB387";
                    case "done"        -> "#A6E3A1";
                    default            -> "#6C7086";
                };
                setStyle("-fx-text-fill: " + color + ";");
            }
        });

        colEffort.setCellValueFactory(d ->
            new SimpleIntegerProperty(d.getValue().GetEffort()).asObject());
        colEffort.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item + "h");
            }
        });

        // Cot Extra: severity cho Bug, developer cho Feature
        colExtra.setCellValueFactory(d -> {
            Task t = d.getValue();
            if (t instanceof Bug)
                return new SimpleStringProperty("Sev: " + ((Bug) t).severity);
            if (t instanceof Feature) {
                Feature f = (Feature) t;
                return new SimpleStringProperty(f.isAssigned() ? "Dev: " + f.getAssignedTo() : "(Chua assign)");
            }
            return new SimpleStringProperty("");
        });
    }

    private void setupFilters() {
        cbFilterStatus.getItems().addAll("(Tat ca)", "todo", "in_progress", "done");
        cbFilterStatus.getSelectionModel().selectFirst();

        cbFilterType.getItems().addAll("(Tat ca)", "B", "F");
        cbFilterType.getSelectionModel().selectFirst();

        cbNewStatus.getItems().addAll("todo", "in_progress", "done");
        cbNewStatus.getSelectionModel().selectFirst();
    }

    private void loadData(List<Task> data) {
        tableView.setItems(FXCollections.observableArrayList(data));
        int total = data.stream().mapToInt(Task::GetEffort).sum();
        lblEffort.setText("Tong effort: " + total + "h  |  So task: " + data.size());
        lblStatus.setText("");
    }

    @FXML
    private void handleFilter() {
        String status = cbFilterStatus.getValue();
        String type   = cbFilterType.getValue();

        List<Task> filtered = "(Tat ca)".equals(status)
            ? service.getAll()
            : service.FilterByStatus(status);

        if (!"(Tat ca)".equals(type)) {
            final String t = type;
            filtered = filtered.stream()
                .filter(task -> task.getTypeCode().equals(t))
                .collect(java.util.stream.Collectors.toList());
        }

        loadData(filtered);
        lblStatus.setText("Dang hien thi: " + filtered.size() + " task.");
    }

    @FXML
    private void handleClearFilter() {
        cbFilterStatus.getSelectionModel().selectFirst();
        cbFilterType.getSelectionModel().selectFirst();
        loadData(service.getAll());
    }

    @FXML
    private void handleUpdateStatus() {
        Task selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) { lblStatus.setText("Vui long chon 1 task."); return; }

        if (service.updateStatus(selected.id, cbNewStatus.getValue())) {
            loadData(service.getAll());
            lblStatus.setText("Da cap nhat: " + selected.id + " → " + cbNewStatus.getValue());
        } else {
            lblStatus.setText("Cap nhat that bai.");
        }
    }

    @FXML
    private void handleDelete() {
        if (!UserSession.isAdmin()) return;   // guard du button da bi an

        Task selected = tableView.getSelectionModel().getSelectedItem();
        if (selected == null) { lblStatus.setText("Vui long chon task can xoa."); return; }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
            "Xoa \"" + selected.title + "\"?", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Xac nhan xoa");
        alert.setHeaderText(null);
        alert.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                if (service.delete(selected.id)) {
                    loadData(service.getAll());
                    lblStatus.setText("Da xoa: " + selected.id);
                } else {
                    lblStatus.setText("Xoa that bai.");
                }
            }
        });
    }

    @FXML
    private void handleReload() {
        lblStatus.setText("Dang tai lai tu DB...");
        service.loadFromDBAsync(() -> {
            loadData(service.getAll());
            lblStatus.setText("Da tai lai du lieu.");
        });
    }

    @FXML
    private void goBack() throws IOException { SceneSwitcher.switchScene("dashboard.fxml"); }
}
