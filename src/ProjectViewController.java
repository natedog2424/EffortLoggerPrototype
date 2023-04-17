import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Assigned to: Evan

public class ProjectViewController implements Initializable{

	
	

	public ArrayList<BacklogItem> SprintBacklog;

	public ArrayList<BacklogItem> CompletedBacklog;

	public ArrayList<BacklogItem> ProductBacklog;
	@FXML
	private ListView<String> ProductBacklogView;
	@FXML
	private ListView<String> SprintBacklogView;
	@FXML
	private ListView<String> CompletedBacklogView;
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.SprintBacklog= new ArrayList<BacklogItem>();
		this.CompletedBacklog = new ArrayList<BacklogItem>();
		this.ProductBacklog = new ArrayList<BacklogItem>();
	}
	@FXML
	protected void ProductToSprint() {
		int index = ProductBacklogView.getSelectionModel().getSelectedIndex();
		SprintBacklog.add(ProductBacklog.get(index));
		SprintBacklogView.getItems().add(ProductBacklog.get(index).backlogToString());
		ProductBacklog.remove(index);
		ProductBacklogView.getItems().clear();
		for(int i = 0; i < ProductBacklog.size(); i++){
			ProductBacklogView.getItems().add(ProductBacklog.get(i).backlogToString());
		}
	}
	@FXML
	protected void SprintToComplete() {
		int index = SprintBacklogView.getSelectionModel().getSelectedIndex();
		CompletedBacklog.add(SprintBacklog.get(index));
		CompletedBacklogView.getItems().add(SprintBacklog.get(index).backlogToString());
		SprintBacklog.remove(index);
		SprintBacklogView.getItems().clear();
		for(int i = 0; i < SprintBacklog.size(); i++){
			SprintBacklogView.getItems().add(SprintBacklog.get(i).backlogToString());
		}
	}
	@FXML
	protected void SprintToProduct() {
		int index = SprintBacklogView.getSelectionModel().getSelectedIndex();
		ProductBacklog.add(SprintBacklog.get(index));
		ProductBacklogView.getItems().add(SprintBacklog.get(index).backlogToString());
		SprintBacklog.remove(index);
		SprintBacklogView.getItems().clear();
		for(int i = 0; i < SprintBacklog.size(); i++){
			SprintBacklogView.getItems().add(SprintBacklog.get(i).backlogToString());
		}
	}
	@FXML
	protected void CompletedToProduct() {
		int index = CompletedBacklogView.getSelectionModel().getSelectedIndex();
		ProductBacklog.add(CompletedBacklog.get(index));
		ProductBacklogView.getItems().add(CompletedBacklog.get(index).backlogToString());
		CompletedBacklog.remove(index);
		CompletedBacklogView.getItems().clear();
		for(int i = 0; i < SprintBacklog.size(); i++){
			CompletedBacklogView.getItems().add(CompletedBacklog.get(i).backlogToString());
		}
	}
	@FXML
	protected void EditProductItem() {
		try{
				int index = ProductBacklogView.getSelectionModel().getSelectedIndex();
				BacklogItem WantToEditItem= ProductBacklog.get(index);
				Stage Edit = new Stage();
				GridPane EditPane = new GridPane();
				
				Label Name = new Label("Name");
				TextField NameField = new TextField(WantToEditItem.BacklogItemName);

				Label EffortValue = new Label("Effort Value Estimation");
				TextField EffortField = new TextField(""+ WantToEditItem.EffortValueEstimation);

				Label Time = new Label("Estimated Time");
				TextField TimeField = new TextField(""+ WantToEditItem.EstimatedTime);

				Button DoneEditing = new Button("Done");
				DoneEditing.setOnAction(e->{
						try{
						String NewName = NameField.getText().trim();
						String NewEffort = EffortField.getText().trim();
						String NewTime = TimeField.getText().trim();
						if(NewName.isEmpty() || NewEffort.isEmpty() || NewTime.isEmpty()){
							throw new Exception("At least one field is empty");
						}
						for(int i = 0; i < ProductBacklog.size(); i++){
							if(ProductBacklog.get(i).BacklogItemName.equals(NewName)){
							throw new Exception("Name can not match another backlog item name!");
							}
						}
						BacklogItem NewItem = new BacklogItem(NewName,NewEffort,NewTime);
						ProductBacklog.set(index, NewItem);
						ProductBacklogView.getItems().clear();
						for(int i = 0; i < ProductBacklog.size(); i++){
							ProductBacklogView.getItems().add(ProductBacklog.get(i).backlogToString());
						}
						
						Edit.close();
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
				EditPane.setHgap(10);
				EditPane.setVgap(10);
				EditPane.setPadding(new Insets(20));
				EditPane.add(Name, 0 ,0);
				EditPane.add(NameField, 1 ,0);
				EditPane.add(EffortValue,  0,1);
				EditPane.add(EffortField, 1 ,1);
				EditPane.add(Time,  0,2);
				EditPane.add(TimeField, 1 ,2);
				VBox EditDisplayCombined = new VBox(10,EditPane,Buttons);
				Edit.setScene(new Scene(EditDisplayCombined, 400, 300));
				Edit.setTitle("Edit Backlog Item");
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
	protected void AddProduct() {
		try{
				Stage Add = new Stage();
				GridPane EditPane = new GridPane();
				
				Label Name = new Label("Name");
				TextField NameField = new TextField();

				Label EffortValue = new Label("Effort Value Estimation");
				TextField EffortField = new TextField();

				Label Time = new Label("Estimated Time");
				TextField TimeField = new TextField();

				Button DoneEditing = new Button("Done");
				DoneEditing.setOnAction(e->{
						try{
						String NewName = NameField.getText().trim();
						String NewEffort = EffortField.getText().trim();
						String NewTime = TimeField.getText().trim();
						if(NewName.isEmpty() || NewEffort.isEmpty() || NewTime.isEmpty()){
							throw new Exception("At least one field is empty");
						}
						for(int i = 0; i < ProductBacklog.size(); i++){
							if(ProductBacklog.get(i).BacklogItemName.equals(NewName)){
							throw new Exception("Name can not match another backlog item name!");
							}
						}
						BacklogItem NewItem = new BacklogItem(NewName,NewEffort,NewTime);
						ProductBacklog.add(NewItem);
						ProductBacklogView.getItems().clear();
						for(int i = 0; i < ProductBacklog.size(); i++){
							ProductBacklogView.getItems().add(ProductBacklog.get(i).backlogToString());
						}
						
						Add.close();
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
				CancelEditing.setOnAction(e -> Add.close());
				HBox Buttons = new HBox(10, DoneEditing, CancelEditing);
				EditPane.setHgap(10);
				EditPane.setVgap(10);
				EditPane.setPadding(new Insets(20));
				EditPane.add(Name, 0 ,0);
				EditPane.add(NameField, 1 ,0);
				EditPane.add(EffortValue,  0,1);
				EditPane.add(EffortField, 1 ,1);
				EditPane.add(Time,  0,2);
				EditPane.add(TimeField, 1 ,2);
				VBox EditDisplayCombined = new VBox(10,EditPane,Buttons);
				Add.setScene(new Scene(EditDisplayCombined, 400, 300));
				Add.setTitle("Edit Backlog Item");
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


}
