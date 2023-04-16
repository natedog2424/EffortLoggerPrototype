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

	public BacklogItem LifeCycleStepLog;

	public Log(BacklogItem LifeCycleStepLog, String Logs, Instant TimeStarted) {
		this.LifeCycleStepLog = LifeCycleStepLog;
		this.Logs = Logs;
		this.TimeStarted = TimeStarted;
		TimeEnded = Instant.now();
		Date = LocalDate.now();
		DurationBetween = Duration.between(TimeStarted, TimeEnded);
	}

	private String formatDuration() {
		long days = DurationBetween.toDays();
    	long hours = DurationBetween.toHours() % 24;
    	long minutes = DurationBetween.toMinutes() % 60;
		return days + ":" + hours + ":" + minutes;
	}

	public String toString() {
		return "LifeCycleStep: " + LifeCycleStepLog.toString() +"\nLogs: " + Logs + "\nDate: " + Date.toString() + "Duration: " + formatDuration();
	}

}
