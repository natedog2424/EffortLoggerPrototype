
//Assigned to: Evan
import java.io.File;
import java.io.IOException;
import java.net.URI;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import java.time.LocalDateTime;	

public class MainViewController implements Initializable {

	String[] TabFXMLFiles = { "EffortConsolePane.fxml", "DefectPane.fxml", "LogsPane.fxml", "ProjectPane.fxml" };
	ScrollPane[] TabPanes = new ScrollPane[4];

	public Pane[] Tabs;

	@FXML
	private Label TimeLabel;

	public ArrayList<Project> Projects;

	private Button DefectsTab;

	private Button LogsTab;

	private Button ProjectTab;

	private Button EffortConsoleTab;

	private ComboBox<String> RoleSelector;

	@FXML
	private Label NameLabel;

	private MenuButton ProjectSelector;
	private Pane DefectsTabPane;

	@FXML
	private MenuButton ProjectMenu;

	private EffortConsoleViewController EffortConsole;

	@FXML
	private VBox TabHolder;

	@FXML
	private ToggleGroup group;

	@FXML
	public Button roleButton;

	private BooleanProperty logsPaneSelected;

	private MediaPlayer mediaPlayer;

	private String capitalizeString(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}

	private void updateTime(){
		//update time
		LocalDateTime now = LocalDateTime.now();
		String time;

		//pad minutes with 0 if needed format in 12 hour time
		if(now.getMinute() < 10){
			time = now.getHour() % 12 + ":0" + now.getMinute();
		}
		else{
			time = now.getHour() % 12 + ":" + now.getMinute();
		}
		
		//get date in format: day of week, month day
		String date = capitalizeString(now.getDayOfWeek().toString()) + ", " + capitalizeString(now.getMonth().toString()) + " " + now.getDayOfMonth();

		//set time and date labels
		TimeLabel.setText(date + " " + time);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// URI audioFileURI = new
		// File("resources/EffortLogger_soundEffect.mp3").toURI();
		// Media audioMedia = new Media(audioFileURI.toString());
		// mediaPlayer = new MediaPlayer(audioMedia);

		//update time and date periodically
		updateTime();

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(1.0), e -> {
					updateTime();
				}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();

		//set name label
		NameLabel.setText(App.user.fullName);

		// load all tabs
		if (App.project.role == null) {
			roleButton.setText("No Role");
		} else {
			roleButton.setText(App.project.role);
		}
		for (int i = 0; i < TabPanes.length; i++) {
			FXMLLoader TabLoader = new FXMLLoader(getClass().getResource(TabFXMLFiles[i]));
			try {
				TabPanes[i] = TabLoader.load();
				// update log table when logspane is selected
				if (i == 0) {
					EffortConsole = TabLoader.getController();

				}

				if (i == 2) {

					LogsViewController controller = TabLoader.getController();
					logsPaneSelected = controller.tabSelectedProperty();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// load tutorial tab
		FXMLLoader TabLoader = new FXMLLoader(getClass().getResource("TutorialPane.fxml"));

		// load tutorial tab
		try {
			ScrollPane TutorialPane = TabLoader.load();
			TabHolder.getChildren().add(TutorialPane);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// initialize listener on tab group change
		group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {

			if (group.getSelectedToggle() != null) {
				// load new tab from selected index
				switchTab(group.getToggles().indexOf(group.getSelectedToggle()));
			}
		});

	}

	@FXML
	protected void smexy() {
		System.out.println("smexy");
	}

	@FXML
	protected void roleButtonPressed() {
		Stage RoleEdit = new Stage();
		RoleEdit.setTitle("Edit Role");
		GridPane RolePane = new GridPane();
		TextField RoleField;

		Label Role = new Label("Role");
		if (roleButton.getText().equals("No Role")) {
			RoleField = new TextField();
		} else {
			RoleField = new TextField(roleButton.getText());
		}
		Button DoneEditing = new Button("Done");
		DoneEditing.setOnAction(e -> {
			try {

				String NewRole = RoleField.getText().trim();
				if (NewRole.isEmpty()) {
					throw new Exception("Please enter a role");
				}
				roleButton.setText(NewRole);
				App.project.changeRole(NewRole);
				RoleEdit.close();
			} catch (Exception exception) {
				Stage error = new Stage();
				Button closeButton = new Button("Close");
				closeButton.setOnAction(errorMes -> error.close());
				Label errorMessage = new Label(exception.getMessage());
				VBox errorHolder = new VBox(10, errorMessage, closeButton);
				errorHolder.setAlignment(Pos.CENTER);
				error.setTitle("Error Message");
				error.setScene(new Scene(errorHolder, 400, 100));
				error.show();
			}
		});
		Button CancelEditing = new Button("Cancel");
		CancelEditing.setOnAction(e -> RoleEdit.close());
		HBox Buttons = new HBox(10, DoneEditing, CancelEditing);
		Buttons.setAlignment(Pos.CENTER);
		RolePane.setAlignment(Pos.CENTER);
		RolePane.setHgap(30);
		RolePane.setVgap(10);
		RolePane.setPadding(new Insets(20));
		RolePane.add(Role, 0, 0);
		RolePane.add(RoleField, 1, 0);
		VBox RoleEditDisplayCombined = new VBox(10, RolePane, Buttons);
		RoleEdit.setScene(new Scene(RoleEditDisplayCombined, 300, 150));

		RoleEdit.show();

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

		// clear ab holder
		TabHolder.getChildren().clear();
		TabHolder.getChildren().add(TabPanes[index]);
		if (index == 0) {
			EffortConsole.FillBacklogComboBox(App.project.SprintBacklog);
		}

		// update table when logs pane is selected
		if (index == 2) {
			logsPaneSelected.set(true);
		} else {
			logsPaneSelected.set(false);
		}
	}

}
