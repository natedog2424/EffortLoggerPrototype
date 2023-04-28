//Assigned to: Noah

import java.net.URL;
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
	private ComboBox<String> SprintNumber;

	@FXML
	private ComboBox<String> BacklogItemName;

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
				String query = "INSERT INTO logs (lifecyclestep, startdate, enddate, duration) VALUES (?, ?, ?, ?)";
				App.dbManager.executeUpdate(query, "test", startTime.format(formatter), endTime.format(formatter), formattedduration);
				Started = false;
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
