
//Assigned to: Noah
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Log {

	public SimpleIntegerProperty id;
	public SimpleStringProperty lifecycleStep;
	public SimpleStringProperty backlogItem;
	public SimpleStringProperty startDate;
	public SimpleStringProperty endDate;
	public SimpleStringProperty duration;

	public Log(int id, String lifecycleStep, String backlogItem, String startDate, String endDate, String duration) {
		this.id = new SimpleIntegerProperty(id);
		this.lifecycleStep = new SimpleStringProperty(lifecycleStep);
		this.backlogItem = new SimpleStringProperty(backlogItem);
		this.startDate = new SimpleStringProperty(startDate);
		this.endDate = new SimpleStringProperty(endDate);
		this.duration = new SimpleStringProperty(duration);
	}
	public Log(){

	}

	public int getId() {
		return id.get();
	}

	public String getLifeCycleStep() {
		return lifecycleStep.get();
	}

	public String getBacklogItem() {
		return backlogItem.get();
	}

	public String getStartDate() {
		return startDate.get();
	}

	public String getEndDate() {
		return endDate.get();
	}

	public String getDuration() {
		return duration.get();
	}
}