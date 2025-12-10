package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class EventValidationTest {
    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);
    LocalDateTime nov_1_2020_22_30 = LocalDateTime.of(2020, 11, 1, 22, 30);
    Duration min_120 = Duration.ofMinutes(120);

    @Test
    public void addExceptionWithoutRepetitionThrows() {
        Event e = new Event("No repeat", nov_1_2020_22_30, min_120);
        assertThrows(IllegalStateException.class, () -> e.addException(nov_1_2020));
    }

    @Test
    public void setTerminationWithoutRepetitionThrows_dateAndCount() {
        Event e = new Event("No repeat", nov_1_2020_22_30, min_120);
        assertThrows(IllegalStateException.class, () -> e.setTermination(nov_1_2020));
        assertThrows(IllegalStateException.class, () -> e.setTermination(5L));
    }

    @Test
    public void gettersReturnZeroOrNullWhenNoTermination() {
        Event e = new Event("No repeat", nov_1_2020_22_30, min_120);
        e.setRepetition(java.time.temporal.ChronoUnit.DAYS);
        // no termination set
        assertEquals(0, e.getNumberOfOccurrences(), "Pas de terminaison => 0 occurrences");
        assertNull(e.getTerminationDate(), "Pas de terminaison => date de terminaison nulle");
    }
}
