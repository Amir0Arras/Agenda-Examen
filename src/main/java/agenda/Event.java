package agenda;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Event {

    /**
     * The myTitle of this event
     */
    private String myTitle;
    
    /**
     * The starting time of the event
     */
    private LocalDateTime myStart;

    /**
     * The durarion of the event 
     */
    private Duration myDuration;

    private Repetition myRepetition = null;

    /**
     * Constructs an event
     *
     * @param title the title of this event
     * @param start the start time of this event
     * @param duration the duration of this event
     */
    public Event(String title, LocalDateTime start, Duration duration) {
        this.myTitle = title;
        this.myStart = start;
        this.myDuration = duration;
    }

    public void setRepetition(ChronoUnit frequency) {
        this.myRepetition = new Repetition(frequency);
    }

    public void addException(LocalDate date) {
        if (myRepetition == null) {
            throw new IllegalStateException("No repetition set");
        }
        myRepetition.addException(date);
    }

    public void setTermination(LocalDate terminationInclusive) {
        if (myRepetition == null) {
            throw new IllegalStateException("No repetition set");
        }
        Termination t = new Termination(myStart.toLocalDate(), myRepetition.getFrequency(), terminationInclusive);
        myRepetition.setTermination(t);
    }

    public void setTermination(long numberOfOccurrences) {
        if (myRepetition == null) {
            throw new IllegalStateException("No repetition set");
        }
        Termination t = new Termination(myStart.toLocalDate(), myRepetition.getFrequency(), numberOfOccurrences);
        myRepetition.setTermination(t);
    }

    public int getNumberOfOccurrences() {
        if (myRepetition == null || myRepetition.getTermination() == null) {
            return 0;
        }
        return (int) myRepetition.getTermination().numberOfOccurrences();
    }

    public LocalDate getTerminationDate() {
        if (myRepetition == null || myRepetition.getTermination() == null) {
            return null;
        }
        return myRepetition.getTermination().terminationDateInclusive();
    }

    /**
     * Tests if an event occurs on a given day
     *
     * @param aDay the day to test
     * @return true if the event occurs on that day, false otherwise
     */
    public boolean isInDay(LocalDate aDay) {
        if (aDay == null) return false;

        // If this event is repetitive and the queried day is an exception,
        // the event must not occur on that entire day (even from a previous occurrence).
        if (myRepetition != null && myRepetition.isException(aDay)) {
            return false;
        }

        LocalDate startDate = myStart.toLocalDate();
        LocalDateTime eventEnd = myStart.plus(myDuration);

        // simple (non-repetitive) event: check interval intersection with day bounds
        if (myRepetition == null) {
            LocalDateTime dayStart = aDay.atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1);
            return myStart.isBefore(dayEnd) && eventEnd.isAfter(dayStart);
        }

        // repetitive event
        ChronoUnit freq = myRepetition.getFrequency();

        if (aDay.isBefore(startDate)) return false;

        // Consider the candidate occurrence starting on that day and the previous occurrence
        long unitsToDay = freq.between(startDate, aDay);
        LocalDate occOnDay = startDate.plus(unitsToDay, freq);
        LocalDate occPrev = occOnDay.minus(1, freq);

        LocalDate[] candidates = new LocalDate[] { occOnDay, occPrev };

        for (LocalDate occDate : candidates) {
            if (occDate.isBefore(startDate)) continue;
            // Check termination if exists
            Termination term = myRepetition.getTermination();
            if (term != null) {
                LocalDate termDate = term.terminationDateInclusive();
                long maxOcc = term.numberOfOccurrences();
                long occIndex = freq.between(startDate, occDate) + 1;
                if (termDate != null && occDate.isAfter(termDate)) continue;
                if (maxOcc > 0 && occIndex > maxOcc) continue;
            }
            // Check exception for the occurrence starting that date
            if (myRepetition.isException(occDate)) continue;

            // Build the occurrence's start/end DateTimes
            LocalDateTime occStart = occDate.atTime(myStart.toLocalTime());
            LocalDateTime occEnd = occStart.plus(myDuration);
            LocalDateTime dayStart = aDay.atStartOfDay();
            LocalDateTime dayEnd = dayStart.plusDays(1);

            if (occStart.isBefore(dayEnd) && occEnd.isAfter(dayStart)) {
                return true;
            }
        }

        return false;
    }
   
    /**
     * @return the myTitle
     */
    public String getTitle() {
        return myTitle;
    }

    /**
     * @return the myStart
     */
    public LocalDateTime getStart() {
        return myStart;
    }


    /**
     * @return the myDuration
     */
    public Duration getDuration() {
        return myDuration;
    }

    @Override
    public String toString() {
        return "Event{title='%s', start=%s, duration=%s}".formatted(myTitle, myStart, myDuration);
    }
}
