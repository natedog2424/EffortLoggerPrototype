//Assigned to Evan
public class Defect {

	public String DefectType;

	public String DefectDescription;



	public int EffortLevel;

	public String Combined;

	public Defect(String Type, String Description, String EffortLevel){
		this.DefectDescription = Description;
		this.DefectType = Type;
		this.EffortLevel = Integer.parseInt(EffortLevel);
		this.Combined = DefectType + "\t\t\t\t" + DefectDescription + "\t\t\t\tEstimate Effort Level: "+ EffortLevel;
	

	}




}
