import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

public class DepartmentForm extends GridPane {
    private TextField deptIdField;
    private TextField deptNameField;
    private TextField helpLineField;
    private TextField locationField;
    private Button addButton;

    private Department departmentToUpdate;
    private boolean formSubmitted = false;

    private Stage ownerStage;

    public DepartmentForm(Department department) {
        this();
        departmentToUpdate = department;
        fillFieldsWithData(departmentToUpdate);

        addButton.setText("Update");
        addButton.setOnAction(e -> updateDepartment());
    }

    public DepartmentForm() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Label deptIdLabel = new Label("Department ID:");
        Label deptNameLabel = new Label("Department Name:");
        Label helpLineLabel = new Label("Help Line:");
        Label locationLabel = new Label("Location:");
        deptIdField = new TextField();
        deptNameField = new TextField();
        helpLineField = new TextField();
        locationField = new TextField();
        addButton = new Button("Add");

        add(deptIdLabel, 0, 0);
        add(deptIdField, 1, 0);
        add(deptNameLabel, 0, 1);
        add(deptNameField, 1, 1);
        add(helpLineLabel, 0, 2);
        add(helpLineField, 1, 2);
        add(locationLabel, 0, 3);
        add(locationField, 1, 3);
        add(addButton, 0, 4);

        addButton.setOnAction(e -> addDepartment());
    }

    private void addDepartment() {
        int deptId = Integer.parseInt(deptIdField.getText());
        String deptName = deptNameField.getText();
        long helpLine = Long.parseLong(helpLineField.getText());
        String location = locationField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.insertDept(deptId, deptName,(long) helpLine, location);
            databaseManager.disconnect();
            clearFields();
            showSuccessMessage("Department added successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error adding department: " + ex.getMessage());
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

    private void updateDepartment() {
        if (departmentToUpdate == null) {
            return;
        }

        int deptId = Integer.parseInt(deptIdField.getText());
        String deptName = deptNameField.getText();
        long helpLine =Long.parseLong(helpLineField.getText());
        String location = locationField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.updateDept(departmentToUpdate.getDeptId(), deptName, helpLine, location);
            databaseManager.disconnect();
            showSuccessMessage("Department updated successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error updating department: " + ex.getMessage());
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void fillFieldsWithData(Department department) {
        deptIdField.setText(String.valueOf(department.getDeptId()));
        deptNameField.setText(department.getDeptName());
        helpLineField.setText(String.valueOf(department.getHelpLine()));
        locationField.setText(department.getLocation());
    }

    private void clearFields() {
        deptIdField.clear();
        deptNameField.clear();
        helpLineField.clear();
        locationField.clear();
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
