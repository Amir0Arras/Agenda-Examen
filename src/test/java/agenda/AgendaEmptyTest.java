package agenda;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class AgendaEmptyTest {

    @Test
    public void emptyAgendaHasNoEvents() {
        Agenda a = new Agenda();
        assertTrue(a.eventsInDay(LocalDate.of(2020,11,1)).isEmpty(), "Agenda vide => pas d'événements");
    }

    @Test
    public void addingNullEventIsIgnored() {
        Agenda a = new Agenda();
        a.addEvent(null);
        assertTrue(a.eventsInDay(LocalDate.of(2020,11,1)).isEmpty(), "Ajouter null ne doit rien faire");
    }
}
