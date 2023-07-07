import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

public class CrimeForm extends GridPane {
    private TextField crimeIdField;
    private TextField crimeNameField;
    private Button addButton;

    private Crime crimeToUpdate;
    private boolean formSubmitted = false;

    private Stage ownerStage;

    public CrimeForm(Crime crime) {
        this();
        crimeToUpdate = crime;
        fillFieldsWithData(crimeToUpdate);

        addButton.setText("Update");
        addButton.setOnAction(e -> updateCrime());
    }

    public CrimeForm() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Label crimeIdLabel = new Label("Crime ID:");
        Label crimeNameLabel = new Label("Crime Name:");
        crimeIdField = new TextField();
        crimeNameField = new TextField();
        addButton = new Button("Add");

        add(crimeIdLabel, 0, 0);
        add(crimeIdField, 1,0);
        add(crimeNameLabel, 0, 1);
        add(crimeNameField, 1, 1);
        add(addButton, 0, 2);

        addButton.setOnAction(e -> addCrime());
    }

    private void addCrime() {
        int crimeId = Integer.parseInt(crimeIdField.getText());
        String crimeName = crimeNameField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.insertCrime(crimeId, crimeName);
            databaseManager.disconnect();
            clearFields();
            showSuccessMessage("Crime added successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error adding crime: " + ex.getMessage());
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

    private void updateCrime() {
        if (crimeToUpdate == null) {
            return;
        }

        int crimeId = Integer.parseInt(crimeIdField.getText());
        String crimeName = crimeNameField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.updateCrime(crimeToUpdate.getCrimeId(), crimeName);
            databaseManager.disconnect();
            showSuccessMessage("Crime updated successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error updating crime: " + ex.getMessage());
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void fillFieldsWithData(Crime crime) {
        crimeIdField.setText(String.valueOf(crime.getCrimeId()));
        crimeNameField.setText(crime.getCrimeName());
    }

    private void clearFields() {
        crimeIdField.clear();
        crimeNameField.clear();
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
