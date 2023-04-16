//Assigned to: Evan

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainViewController
{

	public Pane[] Tabs;

	private Label ClockState;

	public ArrayList<Project> Projects;

	private Button DefectsTab;

	private Button LogsTab;

	private Button ProjectTab;

	private Button EffortConsoleTab;

	private ComboBox<String> RoleSelector;

	private String NameLabel;

	private MenuButton ProjectSelector;

	@FXML
	private StackPane TabHolder;

	private DefectsViewController DefectsTab;

	public MainViewController(Stage stage){
		FXMLLoader fxmlLoader = new FXMLLoader(MainViewController.class.getResource("MainLayout.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 520, 340);
		//Just incase
		stage.setTitle("Effort Logger 2.0");
		stage.setScene(scene);
	}


	private void openEffortConsoleTab() {

	}

	private void openDefectsTab() {

	}

	private void openProjectTab() {

	}

	private void openLogsTab() {

	}

}
