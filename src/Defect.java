//Assigned to Evan
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
public class Defect {

	public String DefectType;

	public String DefectDescription;

	public boolean SelectedDefect;

	public CheckBox selected;

	public Defect(String Type, String Description){
		this.DefectDescription = Description;
		this.DefectType = Type;
		this.SelectedDefect = false;
		String Combined = DefectType + "\t\t" + DefectDescription;
		this.selected = new CheckBox(Combined);

	}
	private class DefectBoxHandler implements EventHandler<ActionEvent>{
		public void handle(AcetionEvent event){
			CheckBox checkSelection = (CheckBox)event.getSource();
			if(check.isSelected()){
				SelectedDefect = true;
			}
			else{
				SelectedDefect = false;
			}
		}
	}




}
