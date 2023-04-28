//Noah McClelland``

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.management.Query;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LogsViewController implements Initializable {

	@FXML
	private TableView<Log> EffortLogs;

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

	private BooleanProperty tabSelected = new SimpleBooleanProperty(false);

	public BooleanProperty tabSelectedProperty() {
		return tabSelected;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tabSelected.addListener((observable, oldValue, newValue) -> {
			try {
				String query = "CREATE TABLE IF NOT EXISTS logs (id INTEGER PRIMARY KEY AUTOINCREMENT, lifecyclestep STRING, startdate STRING, enddate STRING, duration STRING)";
				PreparedStatement statement = App.dbManager.getConnection().prepareStatement(query);
				statement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (newValue) {
				updateTable();
			}
		});
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
					// retrieve input data and insert formatted strings into database
					String startDate = startTimeField.getText().trim();
					String endDate = endTimeField.getText().trim();
					LocalDateTime startTime = LocalDateTime.of(startDatePicker.getValue(),
							LocalTime.of(startHourSpinner.getValue(), startMinuteSpinner.getValue()));
					LocalDateTime endTime = LocalDateTime.of(endDatePicker.getValue(), 
							LocalTime.of(endHourSpinner.getValue(), endMinuteSpinner.getValue()));
					String duration = TimeFormatter.formatDuration(startTime, endTime);
					String query = "INSERT INTO logs (lifecyclestep, startdate, enddate, duration) VALUES (?,?,?,?)";
					App.dbManager.executeUpdate(query, LifeCycleStep.getText(), startDate, endDate, duration);
					updateTable();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
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
	@FXML
	private void editLog() {
		try {
			int index = EffortLogs.getSelectionModel().getSelectedIndex();
			Log WantToEditLog = EffortLogs.getItems().get(index);
			Stage addLogWindow = new Stage();
			GridPane grid = new GridPane();
			Button okBtn = new Button("OK");
			Button cancelBtn = new Button("Cancel");
			Label errorMessage = new Label();
			TextField LifeCycleStep = new TextField();
			TextField EffortCategory = new TextField();

			LifeCycleStep.setText(WantToEditLog.getLifecycleStep());

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
			//update the time field which displays the formatted time given the values of the date picker and spinners
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
						//retrieve input data, format it, and update database
						String startDate = startTimeField.getText().trim();
						String endDate = endTimeField.getText().trim();
						LocalDateTime startTime = LocalDateTime.of(startDatePicker.getValue(),
								LocalTime.of(startHourSpinner.getValue(), startMinuteSpinner.getValue()));
						LocalDateTime endTime = LocalDateTime.of(endDatePicker.getValue(), 
								LocalTime.of(endHourSpinner.getValue(), endMinuteSpinner.getValue()));
						String duration = TimeFormatter.formatDuration(startTime, endTime);
						String query = "UPDATE logs SET lifecyclestep = ?, startdate = ?, enddate = ?, duration = ? WHERE id=?";
						App.dbManager.executeUpdate(query, LifeCycleStep.getText(), startDate, endDate, duration, WantToEditLog.getId());
						updateTable();

					} catch (SQLException ex) {
						ex.printStackTrace();
					}
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
		} catch (Exception ex) {
			Stage error = new Stage();
			Button closeButton = new Button("close");
			closeButton.setOnAction(e -> error.close());
			Label errorMessage = new Label(ex.getMessage());
			VBox errorHolder = new VBox(10, errorMessage, closeButton);
			errorHolder.setAlignment(Pos.CENTER);
			error.setTitle("Error Message");
			error.setScene(new Scene(errorHolder, 400, 100));
			error.show();
		}
	}
	
	public void updateTable() {
		ObservableList<Log> data = FXCollections.observableArrayList();
		try {
			// Get data from database and create log object to be used to as data for display table
			ResultSet resultSet;
			resultSet = App.dbManager.executeQuery("SELECT * FROM logs");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String lifecycleStep = resultSet.getString("lifecyclestep");
				String startDate = resultSet.getString("startdate");
				String endDate = resultSet.getString("enddate");
				String duration = resultSet.getString("duration");
				data.add(new Log(id, lifecycleStep, startDate, endDate, duration));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	
		// define the columns for the table
		TableColumn<Log, String> lifecycleStepColumn = new TableColumn<>("Lifecycle Step");
		lifecycleStepColumn.setCellValueFactory(new PropertyValueFactory<>("lifecycleStep"));

		TableColumn<Log, String> startDateColumn = new TableColumn<>("Date Started");
		startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

		TableColumn<Log, String> endDateColumn = new TableColumn<>("Date Ended");
		endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));

		TableColumn<Log, String> durationColumn = new TableColumn<>("Time Spent");
		durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

		//Set columns and items
		EffortLogs.getColumns().setAll(lifecycleStepColumn, startDateColumn, endDateColumn, durationColumn);
		EffortLogs.setItems(data);

	}

	
	private static void exportToExcel() throws SQLException, IOException {
	
		String query = "SELECT lifecyclestep, startdate, enddate, duration FROM logs";
		ResultSet resultSet = App.dbManager.executeQuery(query);
	
		// Create workbook and sheet
		XSSFWorkbook workbook = new XSSFWorkbook();
		String sheetName = "Sheet1";
		workbook.createSheet(sheetName);
		int sheetIndex = workbook.getSheetIndex(sheetName);
		Row row;
		Cell cell;
	
		//make column headers first row of the sheet
		row = workbook.getSheetAt(sheetIndex).createRow(0);
		int numColumns = resultSet.getMetaData().getColumnCount();
		for (int i = 1; i <= numColumns; i++) {
			cell = row.createCell(i - 1); 
			cell.setCellValue(resultSet.getMetaData().getColumnName(i)); // Set the cell value as the column name
		}
	
		//write data to sheet
		int rowIndex = 1;
		while(resultSet.next()) {
			row = workbook.getSheetAt(sheetIndex).createRow(rowIndex++);
			for (int i = 1; i <= numColumns; i++) {
				cell = row.createCell(i - 1);
				cell.setCellValue(resultSet.getString(i));
			}
		}
		
		File outputFile = new File("resources/logs.xlsx");
		if(!outputFile.exists()) {
			outputFile.createNewFile();
		}
	
		//Write workbook to output file
		FileOutputStream fileOut = new FileOutputStream(outputFile);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
		
	}

@FXML
private void handleExportToExcel(ActionEvent event) {
    try {
        exportToExcel();
    } catch (SQLException e) {
        System.err.println("Error while executing SQL query:");
        e.printStackTrace();
    } catch (IOException e) {
        System.err.println("Error while creating or writing the Excel file:");
        e.printStackTrace();
    }
}
	

}
