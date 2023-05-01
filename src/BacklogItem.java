// Authored by: Nathan Anderson, Noah McLelland, Adit Prabhu, Evan Severtson, and Annalise LaCourse

import java.io.Serializable;

public class BacklogItem implements Serializable{

	public String BacklogItemName;

	public int EffortValueEstimation;

	public float EstimatedTime;
	public BacklogItem(String Name, String Effort, String Time){
		this.BacklogItemName = Name;
		this.EffortValueEstimation = Integer.parseInt(Effort);
		this.EstimatedTime = Float.parseFloat(Time);
	}

	public BacklogItem(){
		
	}
	public String EVString(){
		return ""+EffortValueEstimation;
	}
	public String ETString(){
		return ""+EstimatedTime;
	}

	public String backlogToString(){
		return BacklogItemName + "\t\t\t\t\t\tEstimated Effort Value: " + EffortValueEstimation + "\t\t\t\t\t\tEstimated Time: " + EstimatedTime + " hours";
	}

}
