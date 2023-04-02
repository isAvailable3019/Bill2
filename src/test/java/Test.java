import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {
    @ParameterizedTest
    @CsvFileSource(resources = "/bill.csv", numLinesToSkip = 6)
    void test_csv_file_source(int startYear, int startMouth, int startDay, int startHour, int startMinute, int startSecond
                              ,int endYear, int endMouth, int endDay, int endHour, int endMinute,int endSecond,
                              String fee) {
        LocalDateTime startTime = LocalDateTime.of(startYear, Month.of(startMouth), startDay, startHour, startMinute, startSecond);
        LocalDateTime endTime = LocalDateTime.of(endYear, Month.of(endMouth), endDay, endHour, endMinute, endSecond);
        BigDecimal bill = Bill.calculateFee(Bill.calculateTimeSpan(startTime, endTime));
        assertEquals(fee, String.format("%.2f", bill));
    }
}
