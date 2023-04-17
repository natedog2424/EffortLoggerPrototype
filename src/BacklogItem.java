//Assigned to: Evan

public class BacklogItem {

	public String BacklogItemName;

	public int EffortValueEstimation;

	public int EstimatedTime;
	public BacklogItem(String Name, String Effort, String Time){
		this.BacklogItemName = Name;
		this.EffortValueEstimation = Integer.parseInt(Effort);
		this.EstimatedTime = Integer.parseInt(Time);
	}

	public String backlogToString(){
		return BacklogItemName + "\t\t" + EffortValueEstimation + "\t\t" + EstimatedTime;
	}

}
