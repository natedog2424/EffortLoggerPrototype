//Assigned to: Evan
import java.net.URL;
import java.util.ResourceBundle;

import com.github.javaparser.ast.observer.Observable;

import java.util.ArrayList;

import javafx.beans.property.ReadOnlyIntegerWrapper;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DefectsViewController implements Initializable{
	@FXML
	private TableView<Defect> defectDisplay;
	@FXML
	private TableView<Defect> resolvedDefectDisplay;

	private ObservableList<Defect> UDefects;
	private ObservableList<Defect> RDefects;
	

	Project proj = App.project;

	public Button Combined;

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

	public void initialize(){
		if(proj == null){
			return;
		}

		UDefects = FXCollections.observableArrayList();
		RDefects = FXCollections.observableArrayList();
		initializeTableColumns();
		refreshDefects(UDefects, RDefects);


	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		initialize();
	}
	
	@FXML
	protected void AddUnresolvedDefectEvent() {
		try{Stage Add = new Stage();
		Label Type = new Label("Type");
		GridPane AddPane = new GridPane();
		TextField TypeField = new TextField();
		Label Description = new Label("Description");
		TextField DescriptionField = new TextField();
		TextField EffortField = new TextField();
		Label EffortLabel = new Label("Effort Level");
		Button DoneAdding = new Button("Done");
		DoneAdding.setOnAction(e->{
				try {
				String NewType = TypeField.getText().trim();
				String NewDescription = DescriptionField.getText().trim();
				String NewEffort = EffortField.getText().trim();
				if(NewType.isEmpty() || NewDescription.isEmpty() || NewEffort.isEmpty()){
					throw new Exception("At least one field is empty");
				}
				if(Integer.parseInt(NewEffort)<= 0){
					throw new Exception("Please enter a positive number for estimated effort");
				}
				Defect NewDefect = new Defect(NewType ,NewDescription, NewEffort);
				proj.add(NewDefect,proj.UnresolvedDefects);
				refreshDefects(UDefects, RDefects);
				Add.close();
				}
				catch(NumberFormatException exception){
					Stage error = new Stage();
					Button closeButton = new Button("Close");
					closeButton.setOnAction(errorMes -> error.close());
					Label errorMessage = new Label("Please enter an integer for effort estimation");
					VBox errorHolder = new VBox(10,errorMessage,closeButton);
					errorHolder.setAlignment(Pos.CENTER);
					error.setTitle("Error Message");
					error.setScene(new Scene(errorHolder, 450,100));
					error.show();
				}
				catch(Exception exception){
					Stage error = new Stage();
					Button closeButton = new Button("Close");
					closeButton.setOnAction(x -> error.close());
					Label errorMessage = new Label(exception.getMessage());
					VBox errorHolder = new VBox(10,errorMessage,closeButton);
					errorHolder.setAlignment(Pos.CENTER);
					error.setTitle("Error Message");
					error.setScene(new Scene(errorHolder, 400,100));
					error.show();
				}
		});
				Button CancelAdd = new Button("Cancel");
				CancelAdd.setOnAction(e -> Add.close());
				HBox Buttons = new HBox(10, DoneAdding, CancelAdd);
				Buttons.setAlignment(Pos.CENTER);
				AddPane.setHgap(20);
				AddPane.setVgap(10);
				AddPane.setAlignment(Pos.CENTER);
				AddPane.setPadding(new Insets(20));
				AddPane.add(Type, 0 ,0);
				AddPane.add(TypeField, 1 ,0);
				AddPane.add(Description,  0,1);
				AddPane.add(DescriptionField, 1 ,1);
				AddPane.add(EffortLabel, 0,2);
				AddPane.add(EffortField, 1,2);
				VBox AddDisplayCombined = new VBox(10,AddPane,Buttons);
				Add.setScene(new Scene(AddDisplayCombined, 400, 300));
				Add.setTitle("Add Defect");
        		Add.show();
		}
		catch(Exception exception){
			Stage error = new Stage();
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e -> error.close());
			Label errorMessage = new Label(exception.getMessage());
			VBox errorHolder = new VBox(10,errorMessage,closeButton);
			errorHolder.setAlignment(Pos.CENTER);
			error.setTitle("Error Message");
			error.setScene(new Scene(errorHolder, 400,100));
			error.show();
		}
		

	}

	@FXML
	protected void EditUnresolvedDefectEvent() {
		try{
				int index = defectDisplay.getSelectionModel().getSelectedIndex();
				if(index == -1){
					showErrorWindow("Please select a defect to edit");
					return;
				}
				Defect WantToEditDefect = proj.UnresolvedDefects.get(index);
				Stage Edit = new Stage();
				GridPane EditPane = new GridPane();
				Label Type = new Label("Type");
				TextField TypeField = new TextField(WantToEditDefect.DefectType);
				Label Description = new Label("Description");
				TextField EffortField = new TextField("" + WantToEditDefect.EffortLevel);
				Label EffortLabel = new Label("Effort Level");
				TextField DescriptionField = new TextField(WantToEditDefect.DefectDescription);
				Button DoneEditing = new Button("Done");
				DoneEditing.setOnAction(e->{
						try{String NewType = TypeField.getText().trim();
						String NewDescription = DescriptionField.getText().trim();
						String NewEffort = EffortField.getText().trim();
						if(NewType.isEmpty() || NewDescription.isEmpty() || NewEffort.isEmpty()){
							throw new Exception("At least one field is empty");
						}
						if(Integer.parseInt(NewEffort)<= 0){
							throw new Exception("Please enter a positive number for estimated effort");
						}
						
						Defect NewDefect = new Defect(NewType , NewDescription, NewEffort);
						proj.UnresolvedDefects.set(index, NewDefect);
						defectDisplay.getItems().clear();
						refreshDefects(UDefects, RDefects);
						
						Edit.close();
						}
						catch(NumberFormatException exception){
							Stage error = new Stage();
							Button closeButton = new Button("Close");
							closeButton.setOnAction(errorMes -> error.close());
							Label errorMessage = new Label("Please enter an integer for effort estimation");
							VBox errorHolder = new VBox(10,errorMessage,closeButton);
							errorHolder.setAlignment(Pos.CENTER);
							error.setTitle("Error Message");
							error.setScene(new Scene(errorHolder, 450,100));
							error.show();
						}
						catch(Exception exception){
							Stage error = new Stage();
							Button closeButton = new Button("Close");
							closeButton.setOnAction(errorMes -> error.close());
							Label errorMessage = new Label(exception.getMessage());
							VBox errorHolder = new VBox(10,errorMessage,closeButton);
							errorHolder.setAlignment(Pos.CENTER);
							error.setTitle("Error Message");
							error.setScene(new Scene(errorHolder, 400,100));
							error.show();
						}

				}
				);
				Button CancelEditing = new Button("Cancel");
				CancelEditing.setOnAction(e -> Edit.close());
				HBox Buttons = new HBox(10, DoneEditing, CancelEditing);
				Buttons.setAlignment(Pos.CENTER);
				EditPane.setHgap(20);
				EditPane.setVgap(10);
				EditPane.setAlignment(Pos.CENTER)
				;
				EditPane.setPadding(new Insets(20));
				EditPane.add(Type, 0 ,0);
				EditPane.add(TypeField, 1 ,0);
				EditPane.add(Description,  0,1);
				EditPane.add(DescriptionField, 1 ,1);
				EditPane.add(EffortLabel, 0,2);
				EditPane.add(EffortField, 1,2);
				VBox EditDisplayCombined = new VBox(10,EditPane,Buttons);
				Edit.setScene(new Scene(EditDisplayCombined, 400, 300));
				Edit.setTitle("Edit Defect");
        		Edit.show();

			
		}
		catch(Exception exception){
			Stage error = new Stage();
			Button closeButton = new Button("Close");
			closeButton.setOnAction(e -> error.close());
			Label errorMessage = new Label(exception.getMessage());
			VBox errorHolder = new VBox(10,errorMessage,closeButton);
			errorHolder.setAlignment(Pos.CENTER);
			error.setTitle("Error Message");
			error.setScene(new Scene(errorHolder, 400,100));
			error.show();
		}

	}

	@FXML
	protected void ResolveEvent() {
		int index = defectDisplay.getSelectionModel().getSelectedIndex();
		if(index == -1){
			showErrorWindow("Please select a defect to edit");
			return;
		}
		proj.add(proj.UnresolvedDefects.get(index),proj.ResolvedDefects);
	
		proj.remove(proj.UnresolvedDefects.get(index),proj.UnresolvedDefects);
		defectDisplay.getItems().clear();
		refreshDefects(UDefects, RDefects);
	}
		
	@FXML
	protected void UnresolveEvent() {
		int index = resolvedDefectDisplay.getSelectionModel().getSelectedIndex();
		if(index == -1){
			showErrorWindow("Please select a defect to edit");
			return;
		}
		proj.add(proj.ResolvedDefects.get(index),proj.UnresolvedDefects);
	
		proj.remove(proj.ResolvedDefects.get(index),proj.ResolvedDefects);
		resolvedDefectDisplay.getItems().clear();
		refreshDefects(UDefects, RDefects);
	}


	public void refreshDefects(ObservableList<Defect> UDefects, ObservableList<Defect> RDefects) {
		UDefects.clear();
		RDefects.clear();
	
		UDefects.setAll(proj.UnresolvedDefects);
		RDefects.setAll(proj.ResolvedDefects);
		
		defectDisplay.setItems(UDefects);
		resolvedDefectDisplay.setItems(RDefects);
	}

	public void initializeTableColumns() {
		TableColumn<Defect, String> Name = new TableColumn<>("Type");
		Name.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().DefectType));
		Name.setPrefWidth(100);
	
		TableColumn<Defect, String> desc = new TableColumn<>("Description");
		desc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().DefectDescription));
		desc.setPrefWidth(100);
	
		TableColumn<Defect, String> EL = new TableColumn<>("Estimated Effort");
		EL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().EffortLevelString()));
		EL.setPrefWidth(100);

		TableColumn<Defect, String> RName = new TableColumn<>("Type");
		RName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().DefectType));
		RName.setPrefWidth(100);
	
		TableColumn<Defect, String> Rdesc = new TableColumn<>("Description");
		Rdesc.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().DefectDescription));
		Rdesc.setPrefWidth(100);
	
		TableColumn<Defect, String> REL = new TableColumn<>("Estimated Effort");
		REL.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().EffortLevelString()));
		REL.setPrefWidth(100);
	
		defectDisplay.getColumns().setAll(Name, desc, EL);
		resolvedDefectDisplay.getColumns().setAll(RName, Rdesc, REL);
	}


	
	

}
