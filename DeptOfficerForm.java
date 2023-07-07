import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

public class DeptOfficerForm extends GridPane {
    private TextField officerIdField;
    private TextField nameField;
    private TextField deptIdField;
    private TextField roleField;
    private Button addButton;

    private DeptOfficer officerToUpdate;
    private boolean formSubmitted = false;

    private Stage ownerStage;

    public DeptOfficerForm(DeptOfficer officer) {
        this();
        officerToUpdate = officer;
        fillFieldsWithData(officerToUpdate);

        addButton.setText("Update");
        addButton.setOnAction(e -> updateOfficer());
    }

    public DeptOfficerForm() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Label officerIdLabel = new Label("Officer ID:");
        Label nameLabel = new Label("Name:");
        Label deptIdLabel = new Label("Department ID:");
        Label roleLabel = new Label("Role:");
        officerIdField = new TextField();
        nameField = new TextField();
        deptIdField = new TextField();
        roleField = new TextField();
        addButton = new Button("Add");

        add(officerIdLabel, 0, 0);
        add(officerIdField, 1, 0);
        add(nameLabel, 0, 1);
        add(nameField, 1, 1);
        add(deptIdLabel, 0, 2);
        add(deptIdField, 1, 2);
        add(roleLabel, 0, 3);
        add(roleField, 1, 3);
        add(addButton, 0, 4);

        addButton.setOnAction(e -> addOfficer());
    }

    private void addOfficer() {
        int officerId = Integer.parseInt(officerIdField.getText());
        String name = nameField.getText();
        int deptId = Integer.parseInt(deptIdField.getText());
        String role = roleField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
                       databaseManager.connect();
            databaseManager.insertDeptOfficer(officerId, name, deptId, role);
            databaseManager.disconnect();
            clearFields();
            showSuccessMessage("Department Officer added successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error adding Department Officer: " + ex.getMessage());
        }
    }

    public void setTitle(String title) {
        if (ownerStage != null) {
            ownerStage.setTitle(title);
        }
    }

    public void setOwnerStage(Stage ownerStage) {
        this.ownerStage = ownerStage;
    }

    public void show() {
        Stage stage = new Stage();
        Scene scene = new Scene(this);
        stage.setScene(scene);
        stage.show();
    }

    public boolean isFormSubmitted() {
        return formSubmitted;
    }

    private void updateOfficer() {
        if (officerToUpdate == null) {
            return;
        }

        int officerId = Integer.parseInt(officerIdField.getText());
        String name = nameField.getText();
        int deptId = Integer.parseInt(deptIdField.getText());
        String role = roleField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.updateDeptOfficer(officerToUpdate.getOfficerId(), name, deptId, role);
            databaseManager.disconnect();
            showSuccessMessage("Department Officer updated successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error updating Department Officer: " + ex.getMessage());
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void fillFieldsWithData(DeptOfficer officer) {
        officerIdField.setText(String.valueOf(officer.getOfficerId()));
        nameField.setText(officer.getName());
        deptIdField.setText(String.valueOf(officer.getDeptId()));
        roleField.setText(officer.getRole());
    }

    private void clearFields() {
        officerIdField.clear();
        nameField.clear();
        deptIdField.clear();
        roleField.clear();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
