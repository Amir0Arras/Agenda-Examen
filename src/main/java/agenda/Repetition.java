package agenda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Repetition {
    public ChronoUnit getFrequency() {
        return myFrequency;
    }

    /**
     * Stores the frequency of this repetition, one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     */
    private final ChronoUnit myFrequency;

    // Exceptions to the repetition
    private final List<LocalDate> myExceptions = new ArrayList<>();

    // Optional termination
    private Termination myTermination = null;

    public Repetition(ChronoUnit myFrequency) {
        this.myFrequency = myFrequency;
    }

    /**
     * Les exceptions à la répétition
     * @param date un date à laquelle l'événement ne doit pas se répéter
     */
    public void addException(LocalDate date) {
        // add if not already present
        if (date != null && !myExceptions.contains(date)) {
            myExceptions.add(date);
        }
    }

    /**
     * La terminaison d'une répétition (optionnelle)
     * @param termination la terminaison de la répétition
     */
    public void setTermination(Termination termination) {
        this.myTermination = termination;
    }

    // Utility getters used by Event
    public boolean isException(LocalDate d) {
        return d != null && myExceptions.contains(d);
    }

    public Termination getTermination() {
        return myTermination;
    }
}
