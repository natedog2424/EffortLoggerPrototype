// Authored by: Nathan Anderson, Noah McLelland, Adit Prabhu, Evan Severtson, and Annalise LaCourse
import java.time.Duration;
import java.time.LocalDateTime;

public class TimeFormatter {
    public static String formatDuration(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        long seconds = duration.getSeconds();
        long days = seconds / 86400;
        long hours = (seconds % 86400) / 3600;
        long minutes = (seconds % 3600) / 60;

        return String.format("%dd %02dh %02dm", days, hours, minutes);
    }
}
