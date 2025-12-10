package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TerminationBehaviorTest {
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);
    LocalDateTime nov_1_2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);
    Duration min_120 = Duration.ofMinutes(120);

    @Test
    public void singleOccurrenceLeadsToSameTerminationDate() {
        Event e = new Event("Once", nov_1_2020_22_30, min_120);
        e.setRepetition(ChronoUnit.DAYS);
        e.setTermination(1); // only the start occurrence
        assertEquals(1, e.getNumberOfOccurrences(), "Une occurrence");
        assertEquals(nov_1_2020, e.getTerminationDate(), "La date de terminaison est la date de départ");
    }

    @Test
    public void terminationDateComputedFromCountMoreThanOne() {
        Event e = new Event("Weekly 3 times", nov_1_2020_22_30, min_120);
        e.setRepetition(ChronoUnit.WEEKS);
        e.setTermination(3);
        // start + 2 weeks
        assertEquals(nov_1_2020.plusWeeks(2), e.getTerminationDate());
        assertEquals(3, e.getNumberOfOccurrences());
    }
}
