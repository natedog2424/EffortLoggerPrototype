//Assigned to: Evan

public class BacklogItem {

	public String BacklogItemName;

	public int EffortValueEstimation;

	public float EstimatedTime;
	public BacklogItem(String Name, String Effort, String Time){
		this.BacklogItemName = Name;
		this.EffortValueEstimation = Integer.parseInt(Effort);
		this.EstimatedTime = Float.parseFloat(Time);
	}

	public String backlogToString(){
		return BacklogItemName + "\t\t\t\t\t\tEstimated Effort Value: " + EffortValueEstimation + "\t\t\t\t\t\tEstimated Time: " + EstimatedTime + " hours";
	}

}
