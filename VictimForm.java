import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;

public class VictimForm extends GridPane {
    private TextField idField;
    private TextField nameField;
    private TextField phoneNoField;
    private TextField addressField;
    private Button addButton;

    private Victim victimToUpdate;
    private boolean formSubmitted = false;

    private Stage ownerStage;

    public VictimForm(Victim victim) {
        this();
        victimToUpdate = victim;
        fillFieldsWithData(victimToUpdate);

        addButton.setText("Update");
        addButton.setOnAction(e -> updateVictim());
    }

    public VictimForm() {
        setPadding(new Insets(10));
        setHgap(10);
        setVgap(10);

        Label idLabel = new Label("ID:");
        Label nameLabel = new Label("Name:");
        Label phoneNoLabel = new Label("Phone No:");
        Label addressLabel = new Label("Address:");
        idField = new TextField();
        nameField = new TextField();
        phoneNoField = new TextField();
        addressField = new TextField();
        addButton = new Button("Add");

        add(idLabel, 0, 0);
        add(idField, 1, 0);
        add(nameLabel, 0, 1);
        add(nameField, 1, 1);
        add(phoneNoLabel, 0, 2);
        add(phoneNoField, 1, 2);
        add(addressLabel, 0, 3);
        add(addressField, 1, 3);
        add(addButton, 0, 4);

        addButton.setOnAction(e -> addVictim());
    }

    private void addVictim() {
        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        long phoneNo = Long.parseLong(phoneNoField.getText());
        String address = addressField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.insertVictim(id, name, phoneNo, address);
            databaseManager.disconnect();
            clearFields();
            showSuccessMessage("Victim added successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error adding victim: " + ex.getMessage());
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

    private void updateVictim() {
        if (victimToUpdate == null) {
            return;
        }

        int id = Integer.parseInt(idField.getText());
        String name = nameField.getText();
        long phoneNo = Long.parseLong(phoneNoField.getText());
        String address = addressField.getText();

        try {
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            databaseManager.updateVictim(victimToUpdate.getID(), name, phoneNo, address);
            databaseManager.disconnect();
            showSuccessMessage("Victim updated successfully.");
        } catch (SQLException ex) {
            showErrorMessage("Error updating victim: " + ex.getMessage());
        }

        Stage stage = (Stage) getScene().getWindow();
        stage.close();
    }

    private void fillFieldsWithData(Victim victim) {
        idField.setText(String.valueOf(victim.getID()));
        nameField.setText(victim.getName());
        phoneNoField.setText(String.valueOf(victim.getPhoneNo()));
        addressField.setText(victim.getAddress());
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        phoneNoField.clear();
        addressField.clear();
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
