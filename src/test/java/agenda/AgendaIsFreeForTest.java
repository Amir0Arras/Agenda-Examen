package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaIsFreeForTest {

    @Test
    public void detectsOverlapAndAllowsAdjacent() {
        Agenda agenda = new Agenda();
        Event existing = new Event("Existing", LocalDateTime.of(2022,1,1,10,0), Duration.ofHours(2)); // 10:00-12:00
        agenda.addEvent(existing);

        Event overlap = new Event("Overlap", LocalDateTime.of(2022,1,1,11,0), Duration.ofHours(2)); // 11:00-13:00 -> overlaps
        Event adjacent = new Event("Adjacent", LocalDateTime.of(2022,1,1,12,0), Duration.ofHours(1)); // 12:00-13:00 -> no overlap

        assertFalse(agenda.isFreeFor(overlap), "Le créneau chevauche un événement existant");
        assertTrue(agenda.isFreeFor(adjacent), "Le créneau adjacent (start == existing end) est libre");
    }

    @Test
    public void ignoresRepetitiveEventsWhenCheckingConflicts() {
        Agenda agenda = new Agenda();
        Event repetitive = new Event("Daily", LocalDateTime.of(2022,1,1,10,0), Duration.ofHours(2));
        repetitive.setRepetition(ChronoUnit.DAYS);
        agenda.addEvent(repetitive);

        Event candidate = new Event("Candidate", LocalDateTime.of(2022,1,1,11,0), Duration.ofHours(1));
        // since existing is repetitive, isFreeFor must ignore it and return true
        assertTrue(agenda.isFreeFor(candidate), "Les événements répétitifs existants sont ignorés pour isFreeFor");
    }

    @Test
    public void handlesNullAndRepetitiveCandidate() {
        Agenda agenda = new Agenda();
        assertFalse(agenda.isFreeFor(null), "Null candidate returns false");

        Event candidateRep = new Event("RepCandidate", LocalDateTime.of(2022,1,1,9,0), Duration.ofHours(1));
        candidateRep.setRepetition(ChronoUnit.DAYS);
        assertThrows(IllegalArgumentException.class, () -> agenda.isFreeFor(candidateRep), "isFreeFor doit refuser les candidats répétitifs");
    }
}
