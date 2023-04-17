//Assigned to: Noah

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LogsViewController {

	@FXML
	private TableView EffortLogs;

	private ArrayList<Log> LogsList;

	@FXML
	private Button Add;

	private DatePicker startDatePicker;

	private Spinner<Integer> startHourSpinner;

	private Spinner<Integer> startMinuteSpinner;

	private DatePicker endDatePicker;

	private Spinner<Integer> endHourSpinner;

	private Spinner<Integer> endMinuteSpinner;

	private TextField LifeCycleStep;

	private TextField EffortCategory;

	private TextField startTimeField;

	private TextField endTimeField;

	private void exportToExcel() {

	}

	private void updateTimeField(TextField timeField, LocalDate date, int hour, int minute) {
		LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.of(hour, minute));
		String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		timeField.setText(formattedDateTime);
	}

	@FXML
	public void addLog() {
		Stage addLogWindow = new Stage();
		GridPane grid = new GridPane();
		Button okBtn = new Button("OK");
		Button cancelBtn = new Button("Cancel");
		Label errorMessage = new Label();
		TextField LifeCycleStep = new TextField();
		TextField EffortCategory = new TextField();

		errorMessage.setStyle("-fx-text-fill: red;");

		Label startDateLabel = new Label("Start Date:");
		startDatePicker = new DatePicker(LocalDate.now());

		Label hourLabel = new Label("Hour:");
		startHourSpinner = new Spinner<>(0, 23, 0);

		Label minuteLabel = new Label("Minute:");
		startMinuteSpinner = new Spinner<>(0, 59, 0);

		startTimeField = new TextField();
		startTimeField.setDisable(true);

		Label endDateLabel = new Label("End Date:");
		endDatePicker = new DatePicker(LocalDate.now());
		endHourSpinner = new Spinner<>(0, 23, 0);
		endMinuteSpinner = new Spinner<>(0, 59, 0);

		endTimeField = new TextField();
		endTimeField.setDisable(true);

		startDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateTimeField(startTimeField, startDatePicker.getValue(), startHourSpinner.getValue(),
					startMinuteSpinner.getValue());
		});

		startHourSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateTimeField(startTimeField, startDatePicker.getValue(), startHourSpinner.getValue(),
					startMinuteSpinner.getValue());
		});

		startMinuteSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateTimeField(startTimeField, startDatePicker.getValue(), startHourSpinner.getValue(),
					startMinuteSpinner.getValue());
		});

		endDatePicker.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateTimeField(endTimeField, endDatePicker.getValue(), endHourSpinner.getValue(),
					endMinuteSpinner.getValue());
		});

		endHourSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateTimeField(endTimeField, endDatePicker.getValue(), endHourSpinner.getValue(),
					endMinuteSpinner.getValue());
		});

		endMinuteSpinner.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateTimeField(endTimeField, endDatePicker.getValue(), endHourSpinner.getValue(),
					endMinuteSpinner.getValue());
		});

		cancelBtn.setOnAction(e -> {
			addLogWindow.close();
		});

		okBtn.setOnAction(e -> {
			// Only close and save input if none of the fields are empty
			if (LifeCycleStep.getText().trim().isEmpty() || EffortCategory.getText().trim().isEmpty()
					|| startTimeField.getText().trim().isEmpty() || endTimeField.getText().trim().isEmpty()) {

				errorMessage.setText("At least one entry is empty.");
			}

			else {
				try {
					String dateTimeStr = startTimeField.getText();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr, formatter);
					Instant startInstant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
					String dateTimeStr1 = endTimeField.getText();
					DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					LocalDateTime localDateTime1 = LocalDateTime.parse(dateTimeStr1, formatter1);
					Instant endInstant = localDateTime1.atZone(ZoneId.systemDefault()).toInstant();

					Log log = new Log(startInstant, endInstant);
					System.out.print(log.toString());

				} catch (Exception ex) {
					System.out.println("Invalid date/time format");
				}

				// Save the values that were just entered and clear the input fields
				// log = new Log();
				addLogWindow.close();
			}
		});

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));
		grid.add(new Label("Life Cycle Step:"), 0, 0);
		grid.add(LifeCycleStep, 1, 0);
		grid.add(new Label("Effort Category:"), 0, 1);
		grid.add(EffortCategory, 1, 1);

		HBox buttons = new HBox(10, cancelBtn, okBtn);
		HBox startSpinnerBox = new HBox(hourLabel, startHourSpinner, minuteLabel, startMinuteSpinner);
		HBox endSpinnerBox = new HBox(hourLabel, endHourSpinner, minuteLabel, endMinuteSpinner);
		buttons.setAlignment(Pos.CENTER);
		errorMessage.setAlignment(Pos.CENTER);
		VBox root = new VBox(10, grid, startDateLabel, startDatePicker, startSpinnerBox, startTimeField, endDateLabel,
				endDatePicker, endSpinnerBox, endTimeField, errorMessage, buttons);
		addLogWindow.setScene(new Scene(root, 800, 600));
		addLogWindow.setTitle("Add Log");
		addLogWindow.show();
	}

	private void editLog() {
		//will be implemented maybe at some point 
	}

	public void updateTable() {
		 ObservableList<ResultSet> data = FXCollections.observableArrayList();
		try {
			ResultSet resultSet;
			resultSet = App.dbManager.executeQuery("SELECT * FROM logs");
			while(resultSet.next()) {
				data.add(resultSet);
			}
			EffortLogs.setItems(data);

		} catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

}
