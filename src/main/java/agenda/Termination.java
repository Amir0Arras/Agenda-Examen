package agenda;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;

public class Termination {
    private final LocalDate myTerminationDateInclusive;
    private final long myNumberOfOccurrences;
    private final boolean myFromDate; // true if constructed from a termination date, false if from count

    public LocalDate terminationDateInclusive() {
        return myTerminationDateInclusive;
    }

    public long numberOfOccurrences() {
        return myNumberOfOccurrences;
    }

    public boolean isFromDate() {
        return myFromDate;
    }


    /**
     * Constructs a  termination at a given date
     * @param start the start time of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param terminationInclusive the date when this event ends
     * @see ChronoUnit#between(Temporal, Temporal)
     */
    public Termination(LocalDate start, ChronoUnit frequency, LocalDate terminationInclusive) {
        if (start == null || frequency == null || terminationInclusive == null) {
            throw new IllegalArgumentException("null not allowed");
        }
        // number of intervals between start and terminationInclusive, inclusive occurrences = between + 1
        long between = frequency.between(start, terminationInclusive);
        this.myNumberOfOccurrences = between + 1;
        this.myTerminationDateInclusive = terminationInclusive;
        this.myFromDate = true;
    }

    /**
     * Constructs a fixed termination event ending after a number of iterations
     * @param start the start time of this event
     * @param frequency one of :
     * <UL>
     * <LI>ChronoUnit.DAYS for daily repetitions</LI>
     * <LI>ChronoUnit.WEEKS for weekly repetitions</LI>
     * <LI>ChronoUnit.MONTHS for monthly repetitions</LI>
     * </UL>
     * @param numberOfOccurrences the number of occurrences of this repetitive event
     */
    public Termination(LocalDate start, ChronoUnit frequency, long numberOfOccurrences) {
        if (start == null || frequency == null || numberOfOccurrences <= 0) {
            throw new IllegalArgumentException("invalid arguments");
        }
        this.myNumberOfOccurrences = numberOfOccurrences;
        // termination date is start plus (numberOfOccurrences - 1) intervals
        this.myTerminationDateInclusive = start.plus(numberOfOccurrences - 1, frequency);
        this.myFromDate = false;
    }

}
