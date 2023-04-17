//Assigned to Evan
public class Defect {

	public String DefectType;

	public String DefectDescription;

	public boolean SelectedDefect;

	public String Combined;

	public Defect(String Type, String Description){
		this.DefectDescription = Description;
		this.DefectType = Type;
		this.SelectedDefect = false;
		this.Combined = DefectType + "\t\t\t\t" + DefectDescription;
	

	}




}
