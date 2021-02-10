import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTest {

    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime localDateTime =  LocalDateTime.now();
        LocalDate localDate =LocalDate.now();
        Instant instant=Instant.now();
        System.out.println("Instant:====>"+instant);
        System.out.println("localDateTime:====>"+localDateTime.format(formatter));
        System.out.println("localDate1:====>"+localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("Instant:====>"+instant.atOffset(ZoneOffset.ofHours(8)));
        Date date = new Date(instant.toEpochMilli());
        System.out.println("date:====>"+date);
    }
}
