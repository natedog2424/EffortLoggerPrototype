// Authored by: Nathan Anderson, Noah McLelland, Adit Prabhu, Evan Severtson, and Annalise LaCourse
public class Defect {

	public String DefectType;

	public String DefectDescription;



	public int EffortLevel;


	public Defect(String Type, String Description, String EffortLevel){
		this.DefectDescription = Description;
		this.DefectType = Type;
		this.EffortLevel = Integer.parseInt(EffortLevel);
	}

	public String EffortLevelString(){
		return "" + EffortLevel; 
	}

	public Defect(){}

	public String defectToString(){
		return DefectType + "\t\t\t\t" + DefectDescription + "\t\t\t\tEstimated Effort Level: "+ EffortLevel;
	}

}
