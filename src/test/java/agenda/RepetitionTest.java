package agenda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class RepetitionTest {

    @Test
    public void addExceptionIgnoresNullAndDuplicatesAndReportsException() {
        Repetition r = new Repetition(ChronoUnit.DAYS);
        assertEquals(ChronoUnit.DAYS, r.getFrequency(), "La fréquence doit être DAYS");
        // should not throw
        r.addException(null);
        LocalDate d = LocalDate.of(2020, 11, 3);
        r.addException(d);
        // adding duplicate should be ignored (no exception thrown)
        r.addException(d);
        assertTrue(r.isException(d), "La date doit être marquée comme exception");
        assertFalse(r.isException(null), "null ne doit pas être considéré comme exception");
    }

    @Test
    public void setTerminationIsStoredAndReturned() {
        LocalDate start = LocalDate.of(2020, 11, 1);
        LocalDate term = LocalDate.of(2020, 11, 5);
        Repetition r = new Repetition(ChronoUnit.DAYS);
        Termination t = new Termination(start, r.getFrequency(), term);
        r.setTermination(t);
        assertNotNull(r.getTermination(), "La terminaison doit être accessible après setTermination");
        assertEquals(5, r.getTermination().numberOfOccurrences(), "Nombre d'occurrences calculé");
        assertEquals(term, r.getTermination().terminationDateInclusive(), "Date de terminaison conservée");
    }
}
