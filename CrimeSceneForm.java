import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import java.text.SimpleDateFormat;

public class CrimeSceneForm extends GridPane {
    private TextField victimIdField;
    private TextField crimeNameField;
    private TextField policyNameField;
    private TextField officerIdField;
    private TextField locationField;
    private TextField crimeDateField;
    private Button addButton;

    private CrimeScene crimeSceneToUpdate;
    private boolean formSubmitted = false;

    private Stage ownerStage;

    public CrimeSceneForm(CrimeScene crimeScene) {
        this();
        crimeSceneToUpdate = crimeScene;
        fillFieldsWithData(crimeSceneToUpdate);

        addButton.setText("Update");
        addButton.setOnAction(e -> updateCrimeScene());
    }

    public CrimeSceneForm() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Label victimIdLabel = new Label("Victim ID:");
        Label crimeNameLabel = new Label("Crime Name:");
        Label policyNameLabel = new Label("Policy Name:");
        Label officerIdLabel = new Label("Officer ID:");
        Label locationLabel = new Label("Location:");
        Label crimeDateLabel = new Label("Crime Date:");
        victimIdField = new TextField();
        crimeNameField = new TextField();
        policyNameField = new TextField();
        officerIdField = new TextField();
        locationField = new TextField();
        crimeDateField = new TextField();
        addButton = new Button("Add");

        add(victimIdLabel, 0, 0);
        add(victimIdField, 1, 0);
        add(crimeNameLabel, 0, 1);
        add(crimeNameField, 1, 1);
        add(policyNameLabel, 0, 2);
        add(policyNameField, 1, 2);
        add(officerIdLabel, 0, 3);
        add(officerIdField, 1, 3);
        add(locationLabel, 0, 4);
        add(locationField, 1, 4);
        add(crimeDateLabel, 0, 5);
        add(crimeDateField, 1, 5);
        add(addButton, 0, 6);

        addButton.setOnAction(e -> addCrimeScene());
    }

    private void addCrimeScene() {
        int victimId = Integer.parseInt(victimIdField.getText());
        String crimeName = crimeNameField.getText();
        String policyName = policyNameField.getText();
        int officerId = Integer.parseInt(officerIdField.getText());
        String location = locationField.getText();
        String crimeDate = crimeDateField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.insertCrimeScene(victimId, crimeName, policyName, officerId, location, crimeDate);
            databaseManager.disconnect();
            clearFields();
            showSuccessMessage("Crime Scene added successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error adding Crime Scene: " + ex.getMessage());
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

    private void updateCrimeScene() {
        if (crimeSceneToUpdate == null) {
            return;
        }

        int victimId = Integer.parseInt(victimIdField.getText());
        String crimeName = crimeNameField.getText();
        String policyName = policyNameField.getText();
        int officerId = Integer.parseInt(officerIdField.getText());
        String location = locationField.getText();
        String crimeDate = crimeDateField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.updateCrimeScene(crimeSceneToUpdate.getVictimId(), victimId, crimeName, policyName, officerId, location, crimeDate);
            databaseManager.disconnect();
            showSuccessMessage("Crime Scene updated successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error updating Crime Scene: " + ex.getMessage());
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void fillFieldsWithData(CrimeScene crimeScene) {
        victimIdField.setText(String.valueOf(crimeScene.getVictimId()));
        crimeNameField.setText(crimeScene.getCrimeName());
        policyNameField.setText(crimeScene.getPolicyName());
        officerIdField.setText(String.valueOf(crimeScene.getOfficerId()));
        locationField.setText(crimeScene.getLocation());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(crimeScene.getCrimeDate());
        crimeDateField.setText(formattedDate);
    }

    private void clearFields() {
        victimIdField.clear();
        crimeNameField.clear();
        policyNameField.clear();
        officerIdField.clear();
        locationField.clear();
        crimeDateField.clear();
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
