//Assigned to: Noah

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EffortConsoleViewController implements Initializable {

	@FXML
	private Button StartStopButton;

	@FXML
	private ComboBox<String> LifeCycleStepBox;

	@FXML
	public ComboBox<String> BacklogItemBox;

	private static LocalDateTime startTime;

	private static LocalDateTime endTime;

	public boolean Started = false;

	private boolean changeLCS = false;
	private boolean changeBLI = false;

	private String lifeCycleStep = "";
	private String backlogItem = "";

	@FXML
	private void clockStateEvent() throws SQLException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

		if (!Started) {
			lifeCycleStep = LifeCycleStepBox.getValue();
			backlogItem = BacklogItemBox.getValue();
			if (!(lifeCycleStep == null)) {
				StartStopButton.setText("Stop Clock");
				startTime = startTime.now();
				Started = true;
			} else {
				Stage emptyError = new Stage();
				Button closeButton = new Button("close");
				closeButton.setOnAction(e -> emptyError.close());
				Label errorMessage = new Label("Please select a lifeCycleStep");
				VBox errorHolder = new VBox(10, errorMessage, closeButton);
				errorHolder.setAlignment(Pos.CENTER);
				emptyError.setTitle("Error Message");
				emptyError.setScene(new Scene(errorHolder, 400, 100));
				emptyError.show();
			}
		} else {
			StartStopButton.setText("Start Clock");
			Started = false;
			String newLifeCycleStep = LifeCycleStepBox.getValue();
			String newBacklogItem = BacklogItemBox.getValue();
			endTime = endTime.now();
			String formattedduration = TimeFormatter.formatDuration(startTime, endTime);
			if ((backlogItem != null) && (!backlogItem.equals(BacklogItemBox.getValue())) && !lifeCycleStep.equals(LifeCycleStepBox.getValue())){
				Stage error = new Stage();
				error.initModality(Modality.APPLICATION_MODAL); // Make the dialog modal
				Button keepButton = new Button("Keep");
				Button revertButton = new Button("Revert");
				Label message1 = new Label("You have changed your lifecycle step and backlog item while the clock was running");
				Label message2 = new Label("LifeCycle From: "+ lifeCycleStep + " To: " + newLifeCycleStep);
				Label message3 = new Label("Backlog Item From: "+ backlogItem + " To: " + newBacklogItem);
				Label message4 = new Label("Would you like to keep or revert the changes");

				revertButton.setOnAction(e -> {
					changeLCS = false;
					changeBLI = false;
					error.close();
				});

				keepButton.setOnAction(e -> {
					changeLCS = true;
					changeBLI = true;
					error.close();
				});

				HBox buttons = new HBox(10, revertButton, keepButton);
				buttons.setAlignment(Pos.CENTER);
				VBox errorHolder = new VBox(10, message1, message2, message3, message4, buttons);
				errorHolder.setAlignment(Pos.CENTER);
				error.setTitle("Effort Console Error.");
				error.setScene(new Scene(errorHolder, 500, 300));
				error.showAndWait();
			}
			else if (!lifeCycleStep.equals(LifeCycleStepBox.getValue())){
				Stage error = new Stage();
				error.initModality(Modality.APPLICATION_MODAL); // Make the dialog modal
				Button keepButton = new Button("Keep");
				Button revertButton = new Button("Revert");
				Label message1 = new Label("You have changed your lifecyclestep while the clock was running");
				Label message2 = new Label("from: "+ lifeCycleStep + " to: " + newLifeCycleStep);
				Label message3 = new Label("Would you like to keep or revert the changes");

				revertButton.setOnAction(e -> {
					changeLCS = false;
					error.close();
				});

				keepButton.setOnAction(e -> {
					changeLCS = true;
					error.close();
				});

				HBox buttons = new HBox(10, revertButton, keepButton);
				buttons.setAlignment(Pos.CENTER);
				VBox errorHolder = new VBox(10, message1, message2, message3, buttons);
				errorHolder.setAlignment(Pos.CENTER);
				error.setTitle("Effort Console Error.");
				error.setScene(new Scene(errorHolder, 500, 300));
				error.showAndWait();
			}
			else if ((backlogItem != null) && !backlogItem.equals(BacklogItemBox.getValue())){
				Stage error = new Stage();
				error.initModality(Modality.APPLICATION_MODAL); // Make the dialog modal
				Button keepButton = new Button("Keep");
				Button revertButton = new Button("Revert");
				Label message1 = new Label("You have changed your backlog item while the clock was running");
				Label message2 = new Label("from: "+ backlogItem + " to: " + newBacklogItem);
				Label message3 = new Label("Would you like to keep or revert the changes");

				revertButton.setOnAction(e -> {
					changeBLI = false;
					error.close();
				});

				keepButton.setOnAction(e -> {
					changeBLI = true;
					error.close();
				});

				HBox buttons = new HBox(10, revertButton, keepButton);
				buttons.setAlignment(Pos.CENTER);
				VBox errorHolder = new VBox(10, message1, message2, message3, buttons);
				errorHolder.setAlignment(Pos.CENTER);
				error.setTitle("Effort Console Error.");
				error.setScene(new Scene(errorHolder, 500, 300));
				error.showAndWait();
			}

			if(changeLCS) {
				lifeCycleStep = newLifeCycleStep;
			}
			if(changeBLI) {
				backlogItem = newBacklogItem;
			}

			LifeCycleStepBox.setValue(lifeCycleStep);
			BacklogItemBox.setValue(backlogItem);

			if(backlogItem == null) {
				backlogItem = "----";
			}
			
			try {
				String query = "INSERT INTO logs (lifeCycleStep, backlogItem, startdate, enddate, duration) VALUES (?, ?, ?, ?, ?)";
				App.dbManager.executeUpdate(query, lifeCycleStep, backlogItem, startTime.format(formatter), endTime.format(formatter), formattedduration);
				Started = false;
			} catch(SQLException e) {
				e.printStackTrace();
			}

		}
			
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		BacklogItemBox.setPromptText("(Optional)");
		String[] LifeCycleSteps = {"Problem understanding","Conceptual Design Plan","Requirements", "Conceptual Design", "Conceptual Design View", "Detailed Design Plan","Detailed Design/Prototype","Detailed Design Review", "Implementation Plan", "Test Case Generation", "Solution Specification","Solution Review","Solution Implementation", "Unit/System Test", "Reflection", "Repository Update", "Reflection", "Repository Update", "Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting"};
		LifeCycleStepBox.getItems().addAll(LifeCycleSteps);
	}

	public void FillBacklogComboBox(ArrayList<BacklogItem> SprintBacklogList){
		String selected = BacklogItemBox.getValue();
		BacklogItemBox.getItems().clear();
		for(int i = 0; i < SprintBacklogList.size(); i++){
			BacklogItemBox.getItems().add(SprintBacklogList.get(i).BacklogItemName);
		}
		BacklogItemBox.setValue(selected);
	}

}
