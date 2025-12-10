package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionalEventsTest {
    // November 1st, 2020
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);
    LocalDateTime nov_1_2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);
    Duration min_120 = Duration.ofMinutes(120);

  

    @Test
    public void exceptionOnStartDayPreventsOccurrenceOnStart() {
        Event e = new Event("Excepted start", nov_1_2020_22_30, min_120);
        e.setRepetition(ChronoUnit.DAYS);
        e.addException(nov_1_2020); // exception on start day
        assertFalse(e.isInDay(nov_1_2020), "Exception sur le jour de départ empêche l'occurrence");
        assertTrue(e.isInDay(nov_1_2020.plusDays(1)), "L'occurrence suivante doit avoir lieu");
    }
}
