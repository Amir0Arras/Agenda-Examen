package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class EventTerminationClipTest {

    @Test
    public void terminationOnStartPreventsOverlapNextDay() {
        LocalDateTime start = LocalDateTime.of(2020, 11, 1, 22, 30);
        Event e = new Event("Clip test", start, Duration.ofHours(4)); // overlaps into next day
        e.setRepetition(ChronoUnit.DAYS);
        // termination on start day -> only the start occurrence allowed
        e.setTermination(start.toLocalDate());
        LocalDate dayStart = start.toLocalDate();
        LocalDate dayNext = dayStart.plusDays(1);
        assertTrue(e.isInDay(dayStart), "Doit être présent le jour de départ");
        assertFalse(e.isInDay(dayNext), "Ne doit pas être présent le jour suivant car terminaison empêche le chevauchement");
    }

 
}
