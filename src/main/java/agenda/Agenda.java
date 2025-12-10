package agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Description : An agenda that stores events
 */
public class Agenda {
    // internal storage
    private final List<Event> events = new ArrayList<>();

    /**
     * Adds an event to this agenda
     *
     * @param e the event to add
     */
    public void addEvent(Event e) {
        if (e != null) events.add(e);
    }

    /**
     * Computes the events that occur on a given day
     *
     * @param day the day toi test
     * @return a list of events that occur on that day
     */
    public List<Event> eventsInDay(LocalDate day) {
        List<Event> res = new ArrayList<>();
        for (Event e : events) {
            if (e.isInDay(day)) res.add(e);
        }
        return res;
    }

    /**
     * Trouver les événements de l'agenda en fonction de leur titre
     * @param title le titre à rechercher
     * @return les événements qui ont le même titre
     */
    public List<Event> findByTitle(String title) {
        List<Event> res = new ArrayList<>();
        if (title == null) return res;
        for (Event e : events) {
            if (title.equals(e.getTitle())) {
                res.add(e);
            }
        }
        return res;
    }
    
    /**
     * Déterminer s’il y a de la place dans l'agenda pour un événement (aucun autre événement au même moment)
     * @param e L'événement à tester (on se limitera aux événements sans répétition)
     * @return vrai s’il y a de la place dans l'agenda pour cet événement
     */
    public boolean isFreeFor(Event e) {
        if (e == null) return false;
        // we only handle non-repetitive events as requested
        if (e.isRepetitive()) {
            throw new IllegalArgumentException("isFreeFor supports only non-repetitive events");
        }
        LocalDateTime eStart = e.getStart();
        LocalDateTime eEnd = eStart.plus(e.getDuration());

        for (Event other : events) {
            if (other == e) continue; // ignore le mem object
            // only consider non-repetitive events for conflict checks
            if (other.isRepetitive()) continue;
            LocalDateTime oStart = other.getStart();
            LocalDateTime oEnd = oStart.plus(other.getDuration());
            // overlap if start < otherEnd && end > otherStart
            if (eStart.isBefore(oEnd) && eEnd.isAfter(oStart)) {
                return false;
            }
        }
        return true;
    }
}
