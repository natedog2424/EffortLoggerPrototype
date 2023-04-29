
//Assigned to: Evan
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;

public class MainViewController implements Initializable {

	String[] TabFXMLFiles = { "EffortConsolePane.fxml", "DefectPane.fxml", "LogsPane.fxml", "ProjectPane.fxml" };
	ScrollPane[] TabPanes = new ScrollPane[4];

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
	private Pane DefectsTabPane;

	@FXML
	private VBox TabHolder;

	@FXML
	private ToggleGroup group;

	private BooleanProperty logsPaneSelected;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//load all tabs
		for (int i = 0; i < TabPanes.length; i++) {
			FXMLLoader TabLoader = new FXMLLoader(getClass().getResource(TabFXMLFiles[i]));
			try {
				TabPanes[i] = TabLoader.load();
				// update log table when logspane is selected
				if (i == 2) {
					LogsViewController controller = TabLoader.getController();
					logsPaneSelected = controller.tabSelectedProperty();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//load tutorial tab
		FXMLLoader TabLoader = new FXMLLoader(getClass().getResource("TutorialPane.fxml"));

		//load tutorial tab
		try {
			ScrollPane TutorialPane = TabLoader.load();
			TabHolder.getChildren().add(TutorialPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//initialize listener on tab group change
		group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
			if (group.getSelectedToggle() != null) {
				//load new tab from selected index
				switchTab(group.getToggles().indexOf(group.getSelectedToggle()));
			}
		});

		

	}

	@FXML
	protected void smexy(){
		System.out.println("smexy");
	}

	private void openEffortConsoleTab() {

	}

	private void openDefectsTab() {

	}

	private void openProjectTab() {

	}

	private void openLogsTab() {

	}

	private void switchTab(int index) {
			
			//clear ab holder
			TabHolder.getChildren().clear();
			TabHolder.getChildren().add(TabPanes[index]);

			//update table when logs pane is selected
			if (index == 2) {
				logsPaneSelected.set(true);
			} else {
				logsPaneSelected.set(false);
			}
	}

}
