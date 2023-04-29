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

	private ArrayList<Defect> UnresolvedDefectsList;

	private ArrayList<Defect> ResolvedDefectsList;

	public Button Combined;



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.UnresolvedDefectsList = new ArrayList<Defect>();
		this.ResolvedDefectsList = new ArrayList<Defect>();
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
				Defect NewDefect = new Defect(NewType ,NewDescription, NewEffort);
				UnresolvedDefectsList.add(NewDefect);
				defectDisplay.getItems().add(NewDefect.Combined);
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
				Defect WantToEditDefect = UnresolvedDefectsList.get(index);
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
						
						Defect NewDefect = new Defect(NewType , NewDescription, NewEffort);
						UnresolvedDefectsList.set(index, NewDefect);
						defectDisplay.getItems().clear();
						for(int i = 0; i < UnresolvedDefectsList.size(); i++){
							defectDisplay.getItems().add(UnresolvedDefectsList.get(i).Combined);
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
		ResolvedDefectsList.add(UnresolvedDefectsList.get(index));
		resolvedDefectDisplay.getItems().add(UnresolvedDefectsList.get(index).Combined);
		UnresolvedDefectsList.remove(index);
		defectDisplay.getItems().clear();
		for(int i = 0; i < UnresolvedDefectsList.size(); i++){
			defectDisplay.getItems().add(UnresolvedDefectsList.get(i).Combined);
		}
	}
		
	@FXML
	protected void UnresolveEvent() {
		int index = resolvedDefectDisplay.getSelectionModel().getSelectedIndex();
		UnresolvedDefectsList.add(ResolvedDefectsList.get(index));
		defectDisplay.getItems().add(ResolvedDefectsList.get(index).Combined);
		ResolvedDefectsList.remove(index);
		resolvedDefectDisplay.getItems().clear();
		for(int i = 0; i < ResolvedDefectsList.size(); i++){
			resolvedDefectDisplay.getItems().add(ResolvedDefectsList.get(i).Combined);
		}
	}

	
	

}
