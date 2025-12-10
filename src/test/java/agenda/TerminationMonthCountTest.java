package agenda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TerminationMonthCountTest {

    @Test
    public void monthCountProducesEndOfMonthWhenNeeded() {
        LocalDate start = LocalDate.of(2021, 1, 31);
        Termination t = new Termination(start, ChronoUnit.MONTHS, 2);
        // start + (2-1) months => Feb 28, 2021
        assertEquals(LocalDate.of(2021, 2, 28), t.terminationDateInclusive(), "La terminaison doit être le 28 février 2021");
        assertEquals(2, t.numberOfOccurrences(), "Nombre d'occurrences = 2");
    }

    @Test
    public void dateBasedConstructorComputesCorrectCountForMonths() {
        LocalDate start = LocalDate.of(2021, 1, 31);
        LocalDate term = LocalDate.of(2021, 3, 31);
        Termination t = new Termination(start, ChronoUnit.MONTHS, term);
        // Jan31 -> Mar31 : between months = 2 => occurrences = 3 (Jan, Feb, Mar)
        assertEquals(3, t.numberOfOccurrences(), "Doit y avoir 3 occurrences (Jan, Feb, Mar)");
        assertEquals(term, t.terminationDateInclusive(), "Date de terminaison conservée");
    }
}
