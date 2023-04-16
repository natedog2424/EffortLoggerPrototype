//Assigned to: Noah

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import java.time.Instant;
import java.util.ArrayList;

public class EffortConsoleViewController {

	private Button StartStopButton;

	private ComboBox<String> LifeCycleStep;

	private ComboBox<String> EffortCategory;

	private boolean Started = false;

	private FXMLLoader fxmlLoader;

	public EffortConsoleViewController(ArrayList<String> LifeCycleStepList, ArrayList<String> EffortCategoryList) {
		LifeCycleStep = new ComboBox<>();
		LifeCycleStep.setPromptText("Life Cycle Step");
        LifeCycleStep.getItems().addAll(LifeCycleStepList);

        EffortCategory = new ComboBox<>();
		EffortCategory.setPromptText("Effort Category");
        EffortCategory.getItems().addAll(EffortCategoryList);
	}

	private void clockStateEvent() {

	}

	private void CreateLogObject() {

	}

}
