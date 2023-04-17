//Assigned to: Noah

import java.net.URL;
import java.time.Instant;
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

	private Instant startTime;

	private boolean Started = false;

	@FXML
	private void clockStateEvent() {
		if (!Started) {
			StartStopButton.setText("Stop Clock");
			startTime = Instant.now();
			Started = true;
		} else {
			StartStopButton.setText("Start Clock");
			//CreateLogObject(startTime); unimplemented
			Started = false;
		}
	}

	private void CreateLogObject(Instant time) {
		Log log = new Log(time);
		System.out.print(log.toString());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
