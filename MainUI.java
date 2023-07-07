import javafx.animation.FadeTransition;
import java.util.Optional;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.sql.SQLException;
import javafx.scene.layout.HBox;
import java.util.List;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
public class MainUI extends Application {
    private Stage primaryStage;
	//private VBox container; 

    public static void main(String[] args) {
        launch(args);
    }
	@Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Crime Detection Policies");

        BorderPane root = new BorderPane();

        // Create and configure the animated text
        Text animatedText = new Text("Welcome to Crime Detection Policies");
        animatedText.setFont(Font.font("Arial",FontWeight.BOLD, 40));
        animatedText.setFill(Color.BLACK);

        // Create fade transition for text
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3.5), animatedText);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);

        fadeTransition.play();

        root.setCenter(animatedText);

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root, 900, 900);
        scene.setFill(Color.DARKBLUE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu dataMenu = new Menu("View Data");

        MenuItem displayMenuItem = new MenuItem("Display");
        displayMenuItem.setOnAction(e -> displayData());
        dataMenu.getItems().add(displayMenuItem);


        Menu victimMenu = new Menu("Victims");
        MenuItem victimMenuItem = new MenuItem("Add Victim");
        victimMenuItem.setOnAction(e -> showVictimForm());
        victimMenu.getItems().add(victimMenuItem);

        Menu crimeMenu = new Menu("Crimes");
        MenuItem crimeMenuItem = new MenuItem("Add Crime");
        crimeMenuItem.setOnAction(e -> showCrimeForm());
        crimeMenu.getItems().add(crimeMenuItem);

        Menu departmentMenu = new Menu("Departments");
        MenuItem departmentMenuItem = new MenuItem("Add Department");
        departmentMenuItem.setOnAction(e -> showDepartmentForm());
        departmentMenu.getItems().add(departmentMenuItem);

        Menu officerMenu = new Menu("Department Officers");
        MenuItem officerMenuItem = new MenuItem("Add Department Officer");
        officerMenuItem.setOnAction(e -> showDeptOfficerForm());
        officerMenu.getItems().add(officerMenuItem);

        Menu policyMenu = new Menu("Policies");
        MenuItem policyMenuItem = new MenuItem("Add Policy");
        policyMenuItem.setOnAction(e -> showPolicyForm());
        policyMenu.getItems().add(policyMenuItem);

        Menu crimeSceneMenu = new Menu("Crime Scenes");
        MenuItem crimeSceneMenuItem = new MenuItem("Add Crime Scene");
        crimeSceneMenuItem.setOnAction(e -> showCrimeSceneForm());
        crimeSceneMenu.getItems().add(crimeSceneMenuItem);
		
		
		Menu crimeInfoMenu = new Menu("Crime Info");
        MenuItem locationWiseItem = new MenuItem("CrimeInfo");
        locationWiseItem.setOnAction(event -> CrimeInfo());


        crimeInfoMenu.getItems().addAll(locationWiseItem);

        menuBar.getMenus().addAll(victimMenu, crimeMenu, departmentMenu, officerMenu, policyMenu, crimeSceneMenu,dataMenu,crimeInfoMenu);

        return menuBar;
    }
	
	private VBox container;
	private TableView<Victim> createVictimTable(List<Victim> victims) {
		TableView<Victim> table = new TableView<>();

		TableColumn<Victim, Integer> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn<Victim, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<Victim, Integer> phoneColumn = new TableColumn<>("PhoneNo");
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

		TableColumn<Victim, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

		TableColumn<Victim, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(param -> {
			return new TableCell<Victim, Void>() {
				private final Button updateButton = new Button("Update");
				private final Button deleteButton = new Button("Delete");

				{
					updateButton.setOnAction(event -> {
						Victim victim = (Victim) getTableRow().getItem();
						openVictimForm(victim);
					});

					deleteButton.setOnAction(event -> {
						Victim victim = (Victim) getTableRow().getItem();
						deleteVictim(victim);
					});
				}

				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(new HBox(updateButton, deleteButton));
					}
				}
			};
		});

		table.getColumns().addAll(idColumn, nameColumn, phoneColumn, addressColumn, actionsColumn);
		table.getItems().addAll(victims);

		return table;
	}
	private TableView<Policy> createPolicyTable(List<Policy> policies) {
		TableView<Policy> table = new TableView<>();

		TableColumn<Policy, Integer> policyIdColumn = new TableColumn<>("Policy ID");
		policyIdColumn.setCellValueFactory(new PropertyValueFactory<>("policyId"));

		TableColumn<Policy, String> policyNameColumn = new TableColumn<>("Policy Name");
		policyNameColumn.setCellValueFactory(new PropertyValueFactory<>("policyName"));

		TableColumn<Policy, String> descriptionColumn = new TableColumn<>("Description");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

		TableColumn<Policy, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(param -> {
			return new TableCell<Policy, Void>() {
				private final Button updateButton = new Button("Update");
				private final Button deleteButton = new Button("Delete");

				{
					updateButton.setOnAction(event -> {
						Policy policy = (Policy) getTableRow().getItem();
						openPolicyForm(policy);
					});

					deleteButton.setOnAction(event -> {
						Policy policy = (Policy) getTableRow().getItem();
						deletePolicy(policy);
					});
				}

				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(new HBox(updateButton, deleteButton));
					}
				}
			};
		});

		table.getColumns().addAll(policyIdColumn, policyNameColumn, descriptionColumn, actionsColumn);
		table.getItems().addAll(policies);

		return table;
	}
	private TableView<Department> createDepartmentTable(List<Department> departments) {
		TableView<Department> table = new TableView<>();

		TableColumn<Department, Integer> deptIdColumn = new TableColumn<>("Department ID");
		deptIdColumn.setCellValueFactory(new PropertyValueFactory<>("deptId"));

		TableColumn<Department, String> deptNameColumn = new TableColumn<>("Department Name");
		deptNameColumn.setCellValueFactory(new PropertyValueFactory<>("deptName"));

		TableColumn<Department, Integer> helpLineColumn = new TableColumn<>("HelpLine");
		helpLineColumn.setCellValueFactory(new PropertyValueFactory<>("helpLine"));

		TableColumn<Department, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

		TableColumn<Department, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(param -> {
			return new TableCell<Department, Void>() {
				private final Button updateButton = new Button("Update");
				private final Button deleteButton = new Button("Delete");

				{
					updateButton.setOnAction(event -> {
						Department department = (Department) getTableRow().getItem();
						openDepartmentForm(department);
					});

					deleteButton.setOnAction(event -> {
						Department department = (Department) getTableRow().getItem();
						deleteDepartment(department);
					});
				}

				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(new HBox(updateButton, deleteButton));
					}
				}
			};
		});

		table.getColumns().addAll(deptIdColumn, deptNameColumn, helpLineColumn, locationColumn, actionsColumn);
		table.getItems().addAll(departments);

		return table;
	}
	private TableView<DeptOfficer> createDeptOfficerTable(List<DeptOfficer> deptOfficers) {
		TableView<DeptOfficer> table = new TableView<>();

		TableColumn<DeptOfficer, Integer> officerIdColumn = new TableColumn<>("Officer ID");
		officerIdColumn.setCellValueFactory(new PropertyValueFactory<>("officerId"));

		TableColumn<DeptOfficer, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		TableColumn<DeptOfficer, Integer> deptIdColumn = new TableColumn<>("Department ID");
		deptIdColumn.setCellValueFactory(new PropertyValueFactory<>("deptId"));

		TableColumn<DeptOfficer, String> roleColumn = new TableColumn<>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

		TableColumn<DeptOfficer, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(param -> {
			return new TableCell<DeptOfficer, Void>() {
				private final Button updateButton = new Button("Update");
				private final Button deleteButton = new Button("Delete");

				{
					updateButton.setOnAction(event -> {
						DeptOfficer deptOfficer = (DeptOfficer) getTableRow().getItem();
						openDeptOfficerForm(deptOfficer);
					});

					deleteButton.setOnAction(event -> {
						DeptOfficer deptOfficer = (DeptOfficer) getTableRow().getItem();
						deleteDeptOfficer(deptOfficer);
					});
				}

				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(new HBox(updateButton, deleteButton));
					}
				}
			};
		});

		table.getColumns().addAll(officerIdColumn, nameColumn, deptIdColumn, roleColumn, actionsColumn);
		table.getItems().addAll(deptOfficers);

		return table;
	}
	private TableView<Crime> createCrimeTable(List<Crime> crimes) {
		TableView<Crime> table = new TableView<>();

		TableColumn<Crime, Integer> crimeIdColumn = new TableColumn<>("Crime ID");
		crimeIdColumn.setCellValueFactory(new PropertyValueFactory<>("crimeId"));

		TableColumn<Crime, String> crimeNameColumn = new TableColumn<>("Crime Name");
		crimeNameColumn.setCellValueFactory(new PropertyValueFactory<>("crimeName"));

		TableColumn<Crime, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(param -> {
			return new TableCell<Crime, Void>() {
				private final Button updateButton = new Button("Update");
				private final Button deleteButton = new Button("Delete");

				{
					updateButton.setOnAction(event -> {
						Crime crime = (Crime) getTableRow().getItem();
						openCrimeForm(crime);
					});

					deleteButton.setOnAction(event -> {
						Crime crime = (Crime) getTableRow().getItem();
						deleteCrime(crime);
					});
				}

				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(new HBox(updateButton, deleteButton));
					}
				}
			};
		});

		table.getColumns().addAll(crimeIdColumn, crimeNameColumn, actionsColumn);
		table.getItems().addAll(crimes);

		return table;
	}
	private TableView<CrimeScene> createCrimeSceneTable(List<CrimeScene> crimeScenes) {
		TableView<CrimeScene> table = new TableView<>();

		TableColumn<CrimeScene, Integer> victimIdColumn = new TableColumn<>("Victim ID");
		victimIdColumn.setCellValueFactory(new PropertyValueFactory<>("victimId"));

		TableColumn<CrimeScene, String> crimeNameColumn = new TableColumn<>("Crime Name");
		crimeNameColumn.setCellValueFactory(new PropertyValueFactory<>("crimeName"));

		TableColumn<CrimeScene, String> policyNameColumn = new TableColumn<>("Policy Name");
		policyNameColumn.setCellValueFactory(new PropertyValueFactory<>("policyName"));

		TableColumn<CrimeScene, Integer> officerIdColumn = new TableColumn<>("Officer ID");
		officerIdColumn.setCellValueFactory(new PropertyValueFactory<>("officerId"));

		TableColumn<CrimeScene, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

		TableColumn<CrimeScene, LocalDate> crimeDateColumn = new TableColumn<>("Crime Date");
		crimeDateColumn.setCellValueFactory(new PropertyValueFactory<>("crimeDate"));

		TableColumn<CrimeScene, Void> actionsColumn = new TableColumn<>("Actions");
		actionsColumn.setCellFactory(param -> {
			return new TableCell<CrimeScene, Void>() {
				private final Button updateButton = new Button("Update");
				private final Button deleteButton = new Button("Delete");

				{
					updateButton.setOnAction(event -> {
						CrimeScene crimeScene = (CrimeScene) getTableRow().getItem();
						openCrimeSceneForm(crimeScene);
					});

					deleteButton.setOnAction(event -> {
						CrimeScene crimeScene = (CrimeScene) getTableRow().getItem();
						deleteCrimeScene(crimeScene);
					});
				}

				@Override
				protected void updateItem(Void item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setGraphic(null);
					} else {
						setGraphic(new HBox(updateButton, deleteButton));
					}
				}
			};
		});

		table.getColumns().addAll(victimIdColumn, crimeNameColumn, policyNameColumn, officerIdColumn, locationColumn, crimeDateColumn, actionsColumn);
		table.getItems().addAll(crimeScenes);

		return table;
	}
	



	
	
	



	
    private void displayData() {
        try {
            // Fetch the data from the database using the DatabaseManager class
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
            List<Victim> victims = databaseManager.getAllVictims();
            List<Crime> crimes = databaseManager.getAllCrimes();
            List<Department> departments = databaseManager.getAllDepartments();
            List<DeptOfficer> departmentOfficers = databaseManager.getAllDeptOfficers();
            List<Policy> policies = databaseManager.getAllPolicies();
            List<CrimeScene> crimeScenes = databaseManager.getAllCrimeScenes();
            databaseManager.disconnect();

            // Create UI components to display the data
            TableView<Victim> victimTable = createVictimTable(victims);
            TableView<Crime> crimeTable = createCrimeTable(crimes);
            TableView<Department> departmentTable = createDepartmentTable(departments);
            TableView<DeptOfficer> officerTable = createDeptOfficerTable(departmentOfficers);
            TableView<Policy> policyTable = createPolicyTable(policies);
            TableView<CrimeScene> crimeSceneTable = createCrimeSceneTable(crimeScenes);

            // Create a VBox to hold the UI components
            VBox container = new VBox();
            container.setSpacing(10);
            container.setPadding(new Insets(10));

            // Create the back button
            Button backButton = new Button("Back");
            backButton.setOnAction(e -> showHome());
            container.getChildren().add(backButton);

            // Add the UI components to the container pane
            container.getChildren().addAll(victimTable, crimeTable, departmentTable, officerTable, policyTable,
                    crimeSceneTable);

            // Create a scene and set it in the primaryStage
            Scene scene = new Scene(container, 1000, 1000);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (SQLException ex) {
            showErrorMessage("Error fetching data: " + ex.getMessage());
        }
    }




	private void openCrimeSceneForm(CrimeScene crimeScene) {
		// Create a new instance of the CrimeSceneForm
		CrimeSceneForm crimeSceneForm = new CrimeSceneForm(crimeScene);
		
		// Set the title for the form
		crimeSceneForm.setTitle("Update Crime Scene");
		
		// Show the form
		crimeSceneForm.show();
		
		// Refresh the crime scene table after the form is closed
		if (crimeSceneForm.isFormSubmitted()) {
			displayData();
		}
	}

	private void deleteCrimeScene(CrimeScene crimeScene) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Delete Crime Scene");
		alert.setContentText("Are you sure you want to delete the crime scene?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.connect();
				databaseManager.deleteCrimeScene(crimeScene.getVictimId());
				databaseManager.disconnect();
				showInformationMessage("Crime Scene deleted successfully.");
				displayData();
			} catch (SQLException ex) {
				showErrorMessage("Error deleting crime scene: " + ex.getMessage());
			}
		}
	}

	private void showCrimeSceneForm() {
		CrimeSceneForm crimeSceneForm = new CrimeSceneForm();
		Scene scene = new Scene(crimeSceneForm, 600, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Manage Crime Scenes");
		stage.show();
	}
	private void openPolicyForm(Policy policy) {
		// Create a new instance of the PolicyForm
		PolicyForm policyForm = new PolicyForm(policy);
		
		// Set the title for the form
		policyForm.setTitle("Update Policy");
		
		// Show the form
		policyForm.show();
		
		// Refresh the policy table after the form is closed
		if (policyForm.isFormSubmitted()) {
			displayData();
		}
	}

	private void deletePolicy(Policy policy) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Delete Policy");
		alert.setContentText("Are you sure you want to delete the policy?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.connect();
				databaseManager.deletePolicy(policy.getPolicyId());
				databaseManager.disconnect();
				showInformationMessage("Policy deleted successfully.");
				displayData();
			} catch (SQLException ex) {
				showErrorMessage("Error deleting policy: " + ex.getMessage());
			}
		}
	}

	private void showPolicyForm() {
		PolicyForm policyForm = new PolicyForm();
		Scene scene = new Scene(policyForm, 600, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Manage Policies");
		stage.show();
	}

// Victim

    private void openVictimForm(Victim victim) {
		// Create a new instance of the VictimForm
		VictimForm victimForm = new VictimForm(victim);
		
		// Set the title for the form
		victimForm.setTitle("Update Victim");
		
		// Show the form
		victimForm.show();
		
		// Refresh the victim table after the form is closed
		if (victimForm.isFormSubmitted()) {
			displayData();
		}
	}

	private void deleteVictim(Victim victim) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Delete Victim");
		alert.setContentText("Are you sure you want to delete the victim?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.connect();
				databaseManager.deleteVictim(victim.getID());
				databaseManager.disconnect();
				showInformationMessage("Victim deleted successfully.");
				displayData();
			} catch (SQLException ex) {
				showErrorMessage("Error deleting victim: " + ex.getMessage());
			}
		}
	}

	private void showVictimForm() {
		VictimForm victimForm = new VictimForm();
		Scene scene = new Scene(victimForm, 600, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Manage Victims");
		stage.show();
	}

	// Department

	private void openDepartmentForm(Department department) {
		// Create a new instance of the DepartmentForm
		DepartmentForm departmentForm = new DepartmentForm(department);
		
		// Set the title for the form
		departmentForm.setTitle("Update Department");
		
		// Show the form
		departmentForm.show();
		
		// Refresh the department table after the form is closed
		if (departmentForm.isFormSubmitted()) {
			displayData();
		}
	}

	private void deleteDepartment(Department department) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Delete Department");
        alert.setContentText("Are you sure you want to delete the department?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.connect();
				databaseManager.deleteDept(department.getDeptId());
				databaseManager.disconnect();
				showInformationMessage("Department deleted successfully.");
				displayData();
			} catch (SQLException ex) {
				showErrorMessage("Error deleting department: " + ex.getMessage());
			}
		}
	}
	private void showDepartmentForm() {
		DepartmentForm departmentForm = new DepartmentForm();
		Scene scene = new Scene(departmentForm, 600, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Manage Departments");
		stage.show();
	}
	private void openDeptOfficerForm(DeptOfficer deptOfficer) {
		// Create a new instance of the DeptOfficerForm
		DeptOfficerForm deptOfficerForm = new DeptOfficerForm(deptOfficer);
		// Set the title for the form
		deptOfficerForm.setTitle("Update Department Officer");

		// Show the form
		deptOfficerForm.show();

		// Refresh the deptOfficer table after the form is closed
		if (deptOfficerForm.isFormSubmitted()) {
			displayData();
		}
	}
	private void deleteDeptOfficer(DeptOfficer deptOfficer) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Delete Department Officer");
		alert.setContentText("Are you sure you want to delete the department officer?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.connect();
				databaseManager.deleteDeptOfficer(deptOfficer.getOfficerId());
				databaseManager.disconnect();
				showInformationMessage("Department Officer deleted successfully.");
				displayData();
			} catch (SQLException ex) {
				showErrorMessage("Error deleting department officer: " + ex.getMessage());
			}
	}
	}
	private void showDeptOfficerForm() {
		DeptOfficerForm deptOfficerForm = new DeptOfficerForm();
		Scene scene = new Scene(deptOfficerForm, 600, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Manage Department Officers");
		stage.show();
	}

		// Crime

	private void openCrimeForm(Crime crime) {
		// Create a new instance of the CrimeForm
		CrimeForm crimeForm = new CrimeForm(crime);
		// Set the title for the form
		crimeForm.setTitle("Update Crime");

		// Show the form
		crimeForm.show();

		// Refresh the crime table after the form is closed
		if (crimeForm.isFormSubmitted()) {
			displayData();
		}
	}
	private void deleteCrime(Crime crime) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText("Delete Crime");
		alert.setContentText("Are you sure you want to delete the crime?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			try {
				DatabaseManager databaseManager = new DatabaseManager();
				databaseManager.connect();
				databaseManager.deleteCrime(crime.getCrimeId());
				databaseManager.disconnect();
				showInformationMessage("Crime deleted successfully.");
				displayData();
			} catch (SQLException ex){
				showErrorMessage("Error deleting crime: " + ex.getMessage());
				}
		}
	}

	private void showCrimeForm() {
		CrimeForm crimeForm = new CrimeForm();
		Scene scene = new Scene(crimeForm, 600, 400);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Manage Crimes");
		stage.show();
	}
	private void showHome() {
        primaryStage.setScene(createHomePage());
    }
	private Scene createHomePage() {
		BorderPane root = new BorderPane();

		// Create and configure the animated text
		Text animatedText = new Text("Welcome to Crime Detection Policies");
		animatedText.setFont(Font.font("Arial", FontWeight.BOLD, 40));
		animatedText.setFill(Color.BLACK);

		// Create fade transition for text
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3.5), animatedText);
		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1);
		fadeTransition.setCycleCount(1);

		fadeTransition.play();

		root.setCenter(animatedText);

		// Create the back button
		Button backButton = new Button("Back");
		backButton.setOnAction(e -> showHome());
		root.setBottom(backButton);

		MenuBar menuBar = createMenuBar();
		root.setTop(menuBar);

		Scene scene = new Scene(root, 1200, 1200);
		scene.setFill(Color.DARKBLUE);

		return scene;
	}
	private TableView<LocationCrimeCount> createLocationWiseTable(List<LocationCrimeCount> locationCounts) {
		TableView<LocationCrimeCount> table = new TableView<>();

		TableColumn<LocationCrimeCount, String> locationColumn = new TableColumn<>("Location");
		locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

		TableColumn<LocationCrimeCount, Integer> countColumn = new TableColumn<>("Crime Count");
		countColumn.setCellValueFactory(new PropertyValueFactory<>("crimeCount"));

		table.getColumns().addAll(locationColumn, countColumn);
		table.getItems().addAll(locationCounts);

		return table;
	}
	private TableView<CrimeTypeCount> createCrimeWiseTable(List<CrimeTypeCount> crimeCounts) {
		TableView<CrimeTypeCount> table = new TableView<>();

		TableColumn<CrimeTypeCount, String> crimeColumn = new TableColumn<>("Crime Name");
		crimeColumn.setCellValueFactory(new PropertyValueFactory<>("crimeName"));

		TableColumn<CrimeTypeCount, Integer> countColumn = new TableColumn<>("Crime Count");
		countColumn.setCellValueFactory(new PropertyValueFactory<>("crimeCount"));

		table.getColumns().addAll(crimeColumn, countColumn);
		table.getItems().addAll(crimeCounts);

		return table;
	}
	public void CrimeInfo(){
		try {
            // Fetch the data from the database using the DatabaseManager class
            DatabaseManager databaseManager = new DatabaseManager();
            databaseManager.connect();
			 List<LocationCrimeCount> locationCounts = databaseManager.getCrimeCountsByLocation();
			List<CrimeTypeCount> crimeCounts = databaseManager.getCrimeCountsByType();
			databaseManager.disconnect();
			TableView<LocationCrimeCount> locationTable=createLocationWiseTable(locationCounts);
			TableView<CrimeTypeCount> crimeTable=createCrimeWiseTable(crimeCounts);
			 VBox container = new VBox();
			container.setSpacing(20);
			container.setPadding(new Insets(20));
			Button backButton = new Button("Back");
			backButton.setOnAction(e -> showHome());
			container.getChildren().add(backButton);
			container.getChildren().addAll(locationTable,crimeTable);
			Scene scene = new Scene(container, 1000, 1000);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch (SQLException ex) {
			showErrorMessage("Error fetching data: " + ex.getMessage());
		}
	}

			



	

    private void showInformationMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
