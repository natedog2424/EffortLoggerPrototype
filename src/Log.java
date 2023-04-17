
//Assigned to: Noah
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

public class Log {

	public LocalDate Date;

	public Instant TimeStarted;

	public String Logs;

	public Duration DurationBetween;

	public Instant TimeEnded;

	public BacklogItem BlItem;

	public Log(Instant TimeStarted) {
		// this.LifeCycleStepLog = LifeCycleStepLog;
		// this.Logs = Logs;
		this.TimeStarted = TimeStarted;
		TimeEnded = Instant.now();
		Date = LocalDate.now();
		DurationBetween = Duration.between(TimeStarted, TimeEnded);
	}

	public Log(Instant TimeStarted, Instant TimeEnded) {
		this.TimeStarted = TimeStarted;
		this.TimeEnded = TimeEnded;
		Date = LocalDate.now();
		DurationBetween = Duration.between(TimeStarted, TimeEnded);
	}

	public static void initializeDatabaseTable() {
		App.dbManager.executeUpdate("CREATE TABLE IF NOT EXISTS logs (id INTEGER PRIMARY KEY AUTOINCRIMENT, lifecyclestep STRING, date String, duration STRING)", null)
	}

	private String formatDuration() {
		long days = DurationBetween.toDays();
		long hours = DurationBetween.toHours() % 24;
		long minutes = DurationBetween.toMinutes() % 60;
		return days + ":" + hours + ":" + minutes;
	}

	public String getBacklogItemName() {
		return BlItem.BacklogItemName;
	}

	public int getEffortValueEstimation() {
		return BlItem.EffortValueEstimation;
	}

	public String getLogs() {
		return Logs;
	}

	public String getDate() {
		return Date.toString();
	}

	public String getDuration() {
		return formatDuration();
	}

	public String toString() {
		return "Name:  " + getBacklogItemName() + "Effort Value Estimation: " + getEffortValueEstimation() + "\nDate: "
				+ Date.toString() + "Duration: " + formatDuration();
	}

}