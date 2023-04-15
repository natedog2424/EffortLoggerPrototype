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

	public DefectsViewController(ArrayList<Defect> UnresolvedDefectsInit , ArrayList<Defect> ResolvedDefectsInit){
		this.UnresolvedDefectsList = UnresolvedDefectsInit;
		this.ResolvedDefectsList = ResolvedDefectsInit;
		DefectFXMLPane = new FXMLLoader(DefectsViewController.getResource("DefectPane.fxml"));
	}
	
	@FXML
	protected void AddUnresolvedDefectEvent() {
		Stage add = new Stage();

	}
	@FXML
	protected void EditUnresolvedDefectEvent() {
		try{
			Defect WantToEditDefect;
			for(int i = 0; i < UnresolvedDefectsList.size()-1; i++){
				if(UnresolvedDefectsList.get(i).selected && WantToEditDefect == null){
					WantToEditDefect = UnresolvedDefectsList.get(i);
					continue;
				}
				else if(UnresolvedDefectsList.get(i).selected && WantToEditDefect != null){
					throw new Exception("You can only edit one defect at a time!")
				}
			}
			if(WantToEditDefect == null){
				throw new Exception("Must select at least one defect to edit!")
			}
			else{

			}
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
