package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaFindByTitleTest {

    @Test
    public void findByTitleReturnsMatchingEventsAndIgnoresNullTitle() {
        Agenda agenda = new Agenda();
        Event m1 = new Event("Meeting", LocalDateTime.of(2022,1,1,10,0), Duration.ofHours(1));
        Event m2 = new Event("Meeting", LocalDateTime.of(2022,1,2,10,0), Duration.ofHours(1));
        Event other = new Event("Other", LocalDateTime.of(2022,1,3,9,0), Duration.ofHours(1));
        agenda.addEvent(m1);
        agenda.addEvent(m2);
        agenda.addEvent(other);

        List<Event> meetings = agenda.findByTitle("Meeting");
        assertEquals(2, meetings.size(), "Deux événements avec le titre 'Meeting'");
        assertTrue(meetings.contains(m1) && meetings.contains(m2), "Les deux événements attendus doivent être présents");

        List<Event> empty = agenda.findByTitle(null);
        assertTrue(empty.isEmpty(), "Recherche avec titre null doit renvoyer une liste vide");
    }
}
