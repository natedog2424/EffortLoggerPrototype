//Assigned to: Evan

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class DefectsViewController {
	@FXML
	private Vbox defectDisplay;
	@FXML
	private Vbox resolvedDefectDisplay;

	private Label DefectConsoleLabel;;

	private Pane DefectFXMLPane;

	private Button EditUnresolvedDefectsButton;

	private Button ResolveDefectButton;

	private Button UnresolveDefectButton;

	private ArrayList<Defect> UnresolvedDefectsList;

	private ArrayList<Defect> ResolvedDefectsList;

	public Button Selected;

	private Button AddUnresolvedDefect;

	private Label UnresolvedDefectLabel;

	private Label ResolvedDefectLabel;

	private FXMLLoader fxmlLoader;

	public DefectsViewController(){
		this.UnresolvedDefectsList = new ArrayList<Defect>();
		this.ResolvedDefectsList = new ArrayList<Defect>();
		this.DefectFXMLPane = new FXMLLoader(DefectsViewController.getResource("DefectPane.fxml"));
	}
	
	@FXML
	protected void AddUnresolvedDefectEvent() {
		Stage add = new Stage();
		Label Type = new Label("Type");
		TextField TypeField = new TextField(WantToEditDefect.DefectType);
		Label Description = new Label("Description");
		TextField DescriptionField = new TextField(WantToEditDefect.DefectDescription);
		Button DoneAdding = new Button("Done");
		DoneEditing.setOnAction(e->{
				String NewType = TypeField.getText();
				String NewDescription = DescriptionField.getText();
				if(NewType.isEmpty() || NewDescription.isEmpty()){
					throw new Exception("At least one field is empty");
				}
				Defect NewDefect = new Defect(NewType ,NewDescription);
				UnresolvedDefectsList.add(NewDefect);
				defectDisplay.add(NewDefect.selected);
		}
		Button CancelAdd = new Button("Cancel");
		CancelAdd.setOnAction(e -> Edit.close());
		);

	}

	@FXML
	protected void EditUnresolvedDefectEvent() {
		try{
			Defect WantToEditDefect = new Defect();
			int DefectIndex = -1;
			for(int i = 0; i < UnresolvedDefectsList.size(); i++){
				if(UnresolvedDefectsList.get(i).SelectedDefect && DefectIndex == -1){
					WantToEditDefect = UnresolvedDefectsList.get(i);
					DefectIndex = i;
				}
				else if(UnresolvedDefectsList.get(i).SelectedDefect && DefectIndex != -1){
					throw new Exception("You can only edit one defect at a time!");
					break;
				}
			}
			if(DefectIndex == -1){
				throw new Exception("Must select at least one defect to edit!")
			}
			else{
				Stage Edit = new Stage();
				GridPane EditPane = new GridPane();
				Label Type = new Label("Type");
				TextField TypeField = new TextField(WantToEditDefect.DefectType);
				Label Description = new Label("Description");
				TextField DescriptionField = new TextField(WantToEditDefect.DefectDescription);
				Button DoneEditing = new Button("Done");
				DoneEditing.setOnAction(e->{
						String NewType = TypeField.getText().trim();
						String NewDescription = DescriptionField.getText().trim();
						if(NewType.isEmpty() || NewDescription.isEmpty()){
							throw new Exception("At least one field is empty");
						}
						Defect NewDefect = new Defect(NewType , NewDescription);
						UnresolvedDefectsList.set(DefectIndex, NewDefect);
						defectDisplay.clear();
						for(int i = 0; i < UnresolvedDefectList.size(); i++){
							defectDisplay.add(UnresolvedDefectList.get(i).selected);
						}

				}
				);
				Button CancelEditing = new Button("Cancel");
				CancelEditing.setOnAction(e -> Edit.close());
				Hbox Buttons = new Hbox(10, DoneEditing, CancelEditing);
				EditPane.setHgap(10);
				EditPane.setVgap(10);
				EditPane.setPadding(new Insets(20));
				EditPane.add(Type, 0 ,0);
				EditPane.add(TypeField, 1 ,0);
				EditPane.add(Description,  0,1);
				EditPane.add(DescriptionField, 1 ,1);
				Vbox EditDisplayCombined = new Vbox(10,EditPane,Buttons);
				Edit.setScene(new Scene(EditDisplayCombined, 400, 300));
				Edit.setTitle("Edit Defect");
        		Edit.show();

			}
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

	public void RemoveUnresolvedDefectEvent(ArrayList<Defect> UnresolvedDefectsList) {

	}
	@FXML
	protected void UnresolveEvent() {

	}
	@FXML
	protected void ResolveEvent() {

	}

	public void RemoveResolvedDefectEvent(ArrayList<Defect> ResolvedDefectsList) {

	}

}
