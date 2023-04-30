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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class EffortConsoleViewController implements Initializable {

	@FXML
	private Button StartStopButton;

	@FXML
	private ComboBox<String> LifeCycleStepBox;

	@FXML
	public ComboBox<String> BacklogItemBox;

	private static LocalDateTime startTime;
	private static LocalDateTime endTime;


	private boolean Started = false;

	@FXML
	private void clockStateEvent() throws SQLException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		if (!Started) {
			StartStopButton.setText("Stop Clock");
			startTime = startTime.now();
			Started = true;
		} else {
			StartStopButton.setText("Start Clock");
			endTime = endTime.now();
			String formattedduration = TimeFormatter.formatDuration(startTime, endTime);
			try {
				String query = "INSERT INTO logs (sprintBacklog, startdate, enddate, duration) VALUES (?, ?, ?, ?)";
				App.dbManager.executeUpdate(query, "test", startTime.format(formatter), endTime.format(formatter), formattedduration);
				Started = false;
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String[] LifeCycleSteps = {"Problem understanding","Conceptual Design Plan","Requirements", "Conceptual Design", "Conceptual Design View", "Detailed Design Plan","Detailed Design/Prototype","Detailed Design Review", "Implementation Plan", "Test Case Generation", "Solution Specification","Solution Review","Solution Implementation", "Unit/System Test", "Reflection", "Repository Update", "Reflection", "Repository Update", "Planning", "Information Gathering", "Information Understanding", "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting"};
		LifeCycleStepBox.getItems().addAll(LifeCycleSteps);

		/*try {
			//Create table
			String query = "CREATE TABLE IF NOT EXISTS backlogItems (id INTEGER PRIMARY KEY AUTOINCREMENT, sprintBacklog STRING, productBacklog STRING, completed STRING)";
			PreparedStatement statement = App.dbManager.getConnection().prepareStatement(query);
			statement.execute();
			
			//Add pre-defined items

		} catch (SQLException e) {
			e.printStackTrace();
		}*/
	}

	public void FillBacklogComboBox(ArrayList<BacklogItem> SprintBacklogList){
		BacklogItemBox.getItems().clear();
		for(int i = 0; i < SprintBacklogList.size(); i++){
			BacklogItemBox.getItems().add(SprintBacklogList.get(i).BacklogItemName);
		}
	}

}
