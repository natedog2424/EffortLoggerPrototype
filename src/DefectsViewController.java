//Assigned to: Evan
import java.net.URL;
import java.util.ResourceBundle;
import java.util.ArrayList;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

public class DefectsViewController implements Initializable{
	@FXML
	private ListView<String> defectDisplay;
	@FXML
	private ListView<String> resolvedDefectDisplay;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		defectDisplay.getItems().clear();
		resolvedDefectDisplay.getItems().clear();
		//fill in the listview with the defects
		for(int i = 0; i < proj.UnresolvedDefects.size(); i++){
			defectDisplay.getItems().add(proj.UnresolvedDefects.get(i).defectToString());
		}
		for(int i = 0; i < proj.ResolvedDefects.size(); i++){
			resolvedDefectDisplay.getItems().add(proj.ResolvedDefects.get(i).defectToString());
		}
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
				defectDisplay.getItems().add(NewDefect.defectToString());
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
						for(int i = 0; i < proj.UnresolvedDefects.size(); i++){
							defectDisplay.getItems().add(proj.UnresolvedDefects.get(i).defectToString());
						}
						
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
		resolvedDefectDisplay.getItems().add(proj.UnresolvedDefects.get(index).defectToString());
		proj.remove(proj.UnresolvedDefects.get(index),proj.UnresolvedDefects);
		defectDisplay.getItems().clear();
		for(int i = 0; i < proj.UnresolvedDefects.size(); i++){
			defectDisplay.getItems().add(proj.UnresolvedDefects.get(i).defectToString());
		}
	}
		
	@FXML
	protected void UnresolveEvent() {
		int index = resolvedDefectDisplay.getSelectionModel().getSelectedIndex();
		if(index == -1){
			showErrorWindow("Please select a defect to edit");
			return;
		}
		proj.add(proj.ResolvedDefects.get(index),proj.UnresolvedDefects);
		defectDisplay.getItems().add(proj.ResolvedDefects.get(index).defectToString());
		proj.remove(proj.ResolvedDefects.get(index),proj.ResolvedDefects);
		resolvedDefectDisplay.getItems().clear();
		for(int i = 0; i < proj.ResolvedDefects.size(); i++){
			resolvedDefectDisplay.getItems().add(proj.ResolvedDefects.get(i).defectToString());
		}
	}


	
	

}
