// Authored by: Nathan Anderson, Noah McLelland, Adit Prabhu, Evan Severtson, and Annalise LaCourse
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//Assigned to: Evan

public class ProjectViewController implements Initializable{

	Project proj = App.project;

	@FXML
	private TableView<BacklogItem> ProductBacklogView;
	@FXML
	private TableView<BacklogItem> SprintBacklogView;
	@FXML
	private TableView<BacklogItem> CompletedBacklogView;

	private ObservableList<BacklogItem> PB;
	private ObservableList<BacklogItem> SB;
	private ObservableList<BacklogItem> CB;
	

	@FXML
	private Label ProjectNameLabel;

	public void initialize(){
		ProductBacklogView.getItems().clear();
		SprintBacklogView.getItems().clear();
		CompletedBacklogView.getItems().clear();
		PB = FXCollections.observableArrayList();
		SB = FXCollections.observableArrayList();
		CB = FXCollections.observableArrayList();

		if(proj == null){
			return;
		}
		initializeTableColumns();
		//fill in the list views
		refreshDefects(PB,  SB,  CB);

		ProjectNameLabel.setText(StringUtilities.capitalizeString(proj.Name));
	}

	//initialize function
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initialize();
	}

	private void showErrorWindow(String msg){
		Stage errorWindow = new Stage();
		errorWindow.setTitle("Error");
		Label errorLabel = new Label(msg);
		Button errorButton = new Button("OK");
		errorButton.setOnAction(e -> errorWindow.close());
		VBox errorLayout = new VBox(10);
		errorLayout.getChildren().addAll(errorLabel, errorButton);
		errorLayout.setAlignment(Pos.CENTER);
		Scene errorScene = new Scene(errorLayout, 300, 100);
		errorWindow.setScene(errorScene);
		errorWindow.showAndWait();
	}

	@FXML
	protected void ProductToSprint() {
		int index = ProductBacklogView.getSelectionModel().getSelectedIndex();
		//if index = -1 show error message
		if(index == -1){
			showErrorWindow("Please select a backlog item to move.");
			return;
		}

		proj.add(proj.ProductBacklog.get(index), proj.SprintBacklog);
		proj.remove(proj.ProductBacklog.get(index), proj.ProductBacklog);
		refreshDefects(PB,  SB,  CB);
	}

	@FXML
	protected void SprintToComplete() {
		int index = SprintBacklogView.getSelectionModel().getSelectedIndex();
		//if index = -1 show error message
		if(index == -1){
			showErrorWindow("Please select a backlog item to move.");
			return;
		}
		proj.add(proj.SprintBacklog.get(index), proj.CompletedBacklog);
		proj.remove(proj.SprintBacklog.get(index), proj.SprintBacklog);
		SprintBacklogView.getItems().clear();
		refreshDefects(PB,  SB,  CB);
	}

	@FXML
	protected void SprintToProduct() {
		int index = SprintBacklogView.getSelectionModel().getSelectedIndex();
		//if index = -1 show error message
		if(index == -1){
			showErrorWindow("Please select a backlog item to move.");
			return;
		}
		proj.add(proj.SprintBacklog.get(index), proj.ProductBacklog);
		proj.remove(proj.SprintBacklog.get(index), proj.SprintBacklog);
		SprintBacklogView.getItems().clear();
		refreshDefects(PB,  SB,  CB);
	}

	@FXML
	protected void CompletedToProduct() {
		int index = CompletedBacklogView.getSelectionModel().getSelectedIndex();
		//if index = -1 show error message
		if(index == -1){
			showErrorWindow("Please select a backlog item to move.");
			return;
		}
		proj.add(proj.CompletedBacklog.get(index), proj.ProductBacklog);
		proj.remove(proj.CompletedBacklog.get(index), proj.CompletedBacklog);
		
		refreshDefects(PB,  SB,  CB);
	}

	@FXML
	protected void EditProductItem() {
		try {
			int index = ProductBacklogView.getSelectionModel().getSelectedIndex();
			//if index = -1 show error message
		if(index == -1){
			showErrorWindow("Please select a backlog item to edit.");
			return;
		}
			BacklogItem WantToEditItem = proj.ProductBacklog.get(index);
			Stage Edit = new Stage();
			GridPane EditPane = new GridPane();

			Label Name = new Label("Name");
			TextField NameField = new TextField(WantToEditItem.BacklogItemName);

			Label EffortValue = new Label("Effort Value Estimation");
			TextField EffortField = new TextField("" + WantToEditItem.EffortValueEstimation);

			Label Time = new Label("Estimated Time (Hours)");
			TextField TimeField = new TextField("" + WantToEditItem.EstimatedTime);

			Button DoneEditing = new Button("Done");
			DoneEditing.setOnAction(e -> {
				try {

					String NewName = NameField.getText().trim();
					String NewEffort = EffortField.getText().trim();
					String NewTime = TimeField.getText().trim();
					if (NewName.isEmpty() || NewEffort.isEmpty() || NewTime.isEmpty()) {
						throw new Exception("At least one field is empty");
					}
					if(Integer.parseInt(NewEffort)<= 0){
						throw new Exception("Please enter a positive number for estimated effort");
					}
					if(Float.parseFloat(NewTime) <= 0){
						throw new Exception("Please enter a positive number for estimated time");
					}
					for (int i = 0; i < proj.ProductBacklog.size(); i++) {
						if (index == i) {
							continue;
						} else if (proj.ProductBacklog.get(i).BacklogItemName.equals(NewName)) {
							throw new Exception("Name can not match another backlog item name!");
						}
					}
					BacklogItem NewItem = new BacklogItem(NewName, NewEffort, NewTime);
					proj.ProductBacklog.set(index, NewItem);
					ProductBacklogView.getItems().clear();
					refreshDefects(PB,  SB,  CB);
					Edit.close();
				} catch (NumberFormatException exception) {
					Stage error = new Stage();
					Button closeButton = new Button("Close");
					closeButton.setOnAction(errorMes -> error.close());
					Label errorMessage = new Label(
							"Please enter an integer for effort estimation and any number for esimated time");
					VBox errorHolder = new VBox(10, errorMessage, closeButton);
					errorHolder.setAlignment(Pos.CENTER);
					error.setTitle("Error Message");
					error.setScene(new Scene(errorHolder, 450, 100));
					error.show();
				} catch (IndexOutOfBoundsException ex) {
					Stage error = new Stage();
					Button closeButton = new Button("Close");
					closeButton.setOnAction(errorMes -> error.close());
					Label errorMessage = new Label("Please select an item to edit");
					VBox errorHolder = new VBox(10, errorMessage, closeButton);
					errorHolder.setAlignment(Pos.CENTER);
					error.setTitle("Error Message");
					error.setScene(new Scene(errorHolder, 400, 100));
					error.show();
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
			CancelEditing.setOnAction(e -> Edit.close());
			HBox Buttons = new HBox(10, DoneEditing, CancelEditing);
			Buttons.setAlignment(Pos.CENTER);
			EditPane.setHgap(10);
			EditPane.setVgap(10);
			EditPane.setPadding(new Insets(20));
			EditPane.add(Name, 0, 0);
			EditPane.add(NameField, 1, 0);
			EditPane.add(EffortValue, 0, 1);
			EditPane.add(EffortField, 1, 1);
			EditPane.add(Time, 0, 2);
			EditPane.add(TimeField, 1, 2);
			VBox EditDisplayCombined = new VBox(10, EditPane, Buttons);
			Edit.setScene(new Scene(EditDisplayCombined, 400, 200));
			Edit.setTitle("Edit Backlog Item");

			Edit.show();

		}

		catch (Exception exception) {
			Stage error = new Stage();
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e -> error.close());
			Label errorMessage = new Label(exception.getMessage());
			VBox errorHolder = new VBox(10, errorMessage, closeButton);
			errorHolder.setAlignment(Pos.CENTER);
			error.setTitle("Error Message");
			error.setScene(new Scene(errorHolder, 400, 100));
			error.show();
		}

	}

	@FXML
	protected void AddProduct() {
		try {
			Stage Add = new Stage();
			GridPane EditPane = new GridPane();

			Label Name = new Label("Name");
			TextField NameField = new TextField();

			Label EffortValue = new Label("Effort Value Estimation");
			TextField EffortField = new TextField();

			Label Time = new Label("Estimated Time (Hours)");
			TextField TimeField = new TextField();

			Button DoneEditing = new Button("Done");
			DoneEditing.setOnAction(e -> {
				try {
					String NewName = NameField.getText().trim();
					String NewEffort = EffortField.getText().trim();
					String NewTime = TimeField.getText().trim();
					if (NewName.isEmpty() || NewEffort.isEmpty() || NewTime.isEmpty()) {
						throw new Exception("At least one field is empty");
					}
					if(Integer.parseInt(NewEffort)<= 0){
						throw new Exception("Please enter a positive number for estimated effort");
					}
					if(Float.parseFloat(NewTime) <= 0){
						throw new Exception("Please enter a positive number for estimated time");
					}
					for (int i = 0; i < proj.ProductBacklog.size(); i++) {
						if (proj.ProductBacklog.get(i).BacklogItemName.equals(NewName)) {
							throw new Exception("Name can not match another backlog item name!");
						}
					}
					BacklogItem NewItem = new BacklogItem(NewName, NewEffort, NewTime);
					proj.add(NewItem, proj.ProductBacklog);
					ProductBacklogView.getItems().clear();
					refreshDefects(PB,  SB,  CB);

					Add.close();
				} catch (NumberFormatException exception) {
					Stage error = new Stage();
					Button closeButton = new Button("Close");
					closeButton.setOnAction(errorMes -> error.close());
					Label errorMessage = new Label(
							"Please enter an integer for effort estimation and any number for esimated time");
					VBox errorHolder = new VBox(10, errorMessage, closeButton);
					errorHolder.setAlignment(Pos.CENTER);
					error.setTitle("Error Message");
					error.setScene(new Scene(errorHolder, 450, 100));
					error.show();
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
			CancelEditing.setOnAction(e -> Add.close());
			HBox Buttons = new HBox(10, DoneEditing, CancelEditing);
			EditPane.setHgap(10);
			EditPane.setVgap(10);
			EditPane.setPadding(new Insets(20));
			EditPane.add(Name, 0, 0);
			EditPane.add(NameField, 1, 0);
			EditPane.add(EffortValue, 0, 1);
			EditPane.add(EffortField, 1, 1);
			EditPane.add(Time, 0, 2);
			EditPane.add(TimeField, 1, 2);
			Buttons.setAlignment(Pos.CENTER);
			VBox EditDisplayCombined = new VBox(10, EditPane, Buttons);
			Add.setScene(new Scene(EditDisplayCombined, 400, 200));
			Add.setTitle("Edit Backlog Item");
			Add.show();

		} catch (Exception exception) {
			Stage error = new Stage();
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e -> error.close());
			Label errorMessage = new Label(exception.getMessage());
			VBox errorHolder = new VBox(10, errorMessage, closeButton);
			errorHolder.setAlignment(Pos.CENTER);
			error.setTitle("Error Message");
			error.setScene(new Scene(errorHolder, 400, 100));
			error.show();
		}
	}

	@FXML
	protected void onExportButtonPressed(){
        byte[] serializedProject = ProjectSerializer.serialize(proj);
        try (FileOutputStream fos = new FileOutputStream("project.ser")) {
            fos.write(serializedProject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	@FXML
	protected void onImportButtonPressed() {
		// Create a file chooser dialog to select the file to import
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Project File");
		fileChooser.getExtensionFilters().addAll(
			new FileChooser.ExtensionFilter("Serialized Project Files", "*.ser"),
			new FileChooser.ExtensionFilter("All Files", "*.*")
		);
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			try (FileInputStream fis = new FileInputStream(selectedFile)) {
				byte[] serializedProject = fis.readAllBytes();
				Project importedProject = ProjectSerializer.deserialize(serializedProject);
				proj = importedProject;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void refreshDefects(ObservableList<BacklogItem> PB, ObservableList<BacklogItem> SB, ObservableList<BacklogItem> CB) {
		PB.clear();
		SB.clear();
		CB.clear();
	
		PB.setAll(proj.ProductBacklog);
		SB.setAll(proj.SprintBacklog);
		CB.setAll(proj.CompletedBacklog);
		
		SprintBacklogView.setItems(SB);
		ProductBacklogView.setItems(PB);
		CompletedBacklogView.setItems(CB);
	}

	public void initializeTableColumns() {
		TableColumn<BacklogItem, String> PName = new TableColumn<>("Type");
		PName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().BacklogItemName));
		PName.setPrefWidth(200);
	
		TableColumn<BacklogItem, String> Pdesc = new TableColumn<>("Effort Value");
		Pdesc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().EVString()));
		Pdesc.setPrefWidth(200);
	
		TableColumn<BacklogItem, String> PEL = new TableColumn<>("Estimated Time (Hours)");
		PEL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ETString()));
		PEL.setPrefWidth(200);

		TableColumn<BacklogItem, String> SName = new TableColumn<>("Type");
		SName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().BacklogItemName));
		SName.setPrefWidth(200);
	
		TableColumn<BacklogItem, String> Sdesc = new TableColumn<>("Effort Value");
		Sdesc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().EVString()));
		Sdesc.setPrefWidth(100);
	
		TableColumn<BacklogItem, String> SEL = new TableColumn<>("Estimated Time (Hours)");
		SEL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ETString()));
		SEL.setPrefWidth(200);

		TableColumn<BacklogItem, String> CName = new TableColumn<>("Type");
		CName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().BacklogItemName));
		CName.setPrefWidth(200);
	
		TableColumn<BacklogItem, String> Cdesc = new TableColumn<>("Effort Value");
		Cdesc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().EVString()));
		Cdesc.setPrefWidth(200);
	
		TableColumn<BacklogItem, String> CEL = new TableColumn<>("Estimated Time (Hours)");
		CEL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ETString()));
		CEL.setPrefWidth(200);
	
		ProductBacklogView.getColumns().setAll(PName, Pdesc, PEL);
		SprintBacklogView.getColumns().setAll(SName, Sdesc, SEL);
		CompletedBacklogView.getColumns().setAll(CName, Cdesc, CEL);
	}


}
