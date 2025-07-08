import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class LauncherManager {
    public static void main(String[] args){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime now = LocalTime.of(20,35,0);
        LocalTime watchHour = LocalTime.of(20,34,23);
        // 20:34:17
        System.out.println(now.until(watchHour, ChronoUnit.SECONDS));

        System.out.println(LocalDate.now());
    }
}
