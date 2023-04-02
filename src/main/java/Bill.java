import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.zone.ZoneRules;

public class Bill {
    public static boolean isDaylightTime(LocalDateTime a, ZoneId dest) {
        ZonedDateTime z1 = a.atZone(dest);
        ZoneRules rules = dest.getRules();
        return rules.isDaylightSavings(z1.toInstant());
    }

    public static long calculateTimeSpan(LocalDateTime startTime, LocalDateTime endTime) {
        Duration duration = Duration.between(startTime, endTime);
        boolean start = isDaylightTime(startTime, ZoneId.of("America/New_York"));
        boolean end = isDaylightTime(endTime, ZoneId.of("America/New_York"));
        if(start ^ end){
            duration = duration.plusMillis(start ? 3600000: -3600000);
        }
        return duration.toMillis();
    }

    public static BigDecimal calculateFee(long timeLength) {
        long time = timeLength / 1000 / 60 + (timeLength % 60000 > 0 ? 1 : 0);
        if (time > 1800 || time <= 0) {
            return BigDecimal.valueOf(-1);
        } else if (time < 20) {
            return new BigDecimal(time * 5 / 100.0);
        } else{
            return BigDecimal.valueOf(1 + (time - 20) * 0.1);
        }
    }
}
