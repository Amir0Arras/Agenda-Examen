package agenda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TerminationValidationTest {

    @Test
    public void constructorThrowsOnNullArguments_dateTermination() {
        LocalDate valid = LocalDate.of(2020, 11, 1);
        // null start
        assertThrows(IllegalArgumentException.class, () -> new Termination(null, ChronoUnit.DAYS, valid));
        // null frequency
        assertThrows(IllegalArgumentException.class, () -> new Termination(valid, null, valid));
        // null termination date
        assertThrows(IllegalArgumentException.class, () -> new Termination(valid, ChronoUnit.DAYS, null));
    }

    @Test
    public void constructorThrowsOnInvalidNumberOfOccurrences() {
        LocalDate valid = LocalDate.of(2020, 11, 1);
        // zero or negative occurrences not allowed
        assertThrows(IllegalArgumentException.class, () -> new Termination(valid, ChronoUnit.DAYS, 0L));
        assertThrows(IllegalArgumentException.class, () -> new Termination(valid, ChronoUnit.DAYS, -5L));
    }
}
