import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

public class PolicyForm extends GridPane {
    private TextField policyIdField;
    private TextField policyNameField;
    private TextField descriptionField;
    private Button addButton;

    private Policy policyToUpdate;
    private boolean formSubmitted = false;

    private Stage ownerStage;

    public PolicyForm(Policy policy) {
        this();
        policyToUpdate = policy;
        fillFieldsWithData(policyToUpdate);

        addButton.setText("Update");
        addButton.setOnAction(e -> updatePolicy());
    }

    public PolicyForm() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Label policyIdLabel = new Label("Policy ID:");
        Label policyNameLabel = new Label("Policy Name:");
        Label descriptionLabel = new Label("Description:");
        policyIdField = new TextField();
        policyNameField = new TextField();
        descriptionField = new TextField();
        addButton = new Button("Add");

        add(policyIdLabel, 0, 0);
        add(policyIdField, 1, 0);
        add(policyNameLabel, 0, 1);
        add(policyNameField, 1, 1);
        add(descriptionLabel, 0, 2);
        add(descriptionField, 1, 2);
        add(addButton, 0, 3);

        addButton.setOnAction(e -> addPolicy());
    }

    private void addPolicy() {
        int policyId = Integer.parseInt(policyIdField.getText());
        String policyName = policyNameField.getText();
        String description = descriptionField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.insertPolicy(policyId, policyName, description);
            databaseManager.disconnect();
            clearFields();
            showSuccessMessage("Policy added successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error adding policy: " + ex.getMessage());
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

    private void updatePolicy() {
        if (policyToUpdate == null) {
            return;
        }

        int policyId = Integer.parseInt(policyIdField.getText());
        String policyName = policyNameField.getText();
        String description = descriptionField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.updatePolicy(policyToUpdate.getPolicyId(), policyName, description);
            databaseManager.disconnect();
            showSuccessMessage("Policy updated successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error updating policy: " + ex.getMessage());
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void fillFieldsWithData(Policy policy) {
        policyIdField.setText(String.valueOf(policy.getPolicyId()));
        policyNameField.setText(policy.getPolicyName());
        descriptionField.setText(policy.getDescription());
    }

    private void clearFields() {
        policyIdField.clear();
        policyNameField.clear();
        descriptionField.clear();
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
