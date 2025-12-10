package agenda;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class MoreEventRepetitionTests {

    LocalDate nov_1_2020 = LocalDate.of(2020, 11, 1);
    LocalDateTime nov_1_2020_23_30 = LocalDateTime.of(2020, 11, 1, 23, 30);

    @Test
    public void repetitionAddExceptionHandlesNullAndDuplicates() {
        Repetition r = new Repetition(ChronoUnit.DAYS);
        // add null should be ignored (no exception thrown)
        r.addException(null);
        LocalDate d = LocalDate.of(2020, 11, 3);
        r.addException(d);
        // adding duplicate shouldn't break behaviour
        r.addException(d);
        assertTrue(r.isException(d), "La date doit être enregistrée comme exception");
        assertFalse(r.isException(null), "null ne doit pas être considéré comme exception");
    }

    @Test
    public void exceptionPreventsOverlapFromPreviousOccurrence() {
        // événement démarrant J à 23:30, durée 2h -> chevauche J+1
        Event e = new Event("Late event", nov_1_2020_23_30, Duration.ofMinutes(120));
        e.setRepetition(ChronoUnit.DAYS);
        // rendre J+1 une exception : l'événement ne doit pas apparaître ce jour (même s'il déborde depuis J)
        LocalDate dayPlus1 = nov_1_2020.plusDays(1);
        e.addException(dayPlus1);
        assertFalse(e.isInDay(dayPlus1), "L'exception empêche l'occurrence même si elle provient du chevauchement");
        // J lui-même doit encore être vrai
        assertTrue(e.isInDay(nov_1_2020));
    }

    @Test
    public void monthlyRepetitionHandlesEndOfMonth() {
        // démarrer le 31 Jan 2021 à 10:00, répétition mensuelle
        LocalDateTime jan31_2021_10 = LocalDateTime.of(2021, 1, 31, 10, 0);
        Event e = new Event("Month end", jan31_2021_10, Duration.ofMinutes(60));
        e.setRepetition(ChronoUnit.MONTHS);
        // Feb 2021 -> 28
        assertTrue(e.isInDay(LocalDate.of(2021, 2, 28)), "L'occurrence mensuelle doit se produire le 28 février (ajustement de fin de mois)");
        // Mar 31 exists
        assertTrue(e.isInDay(LocalDate.of(2021, 3, 31)), "L'occurrence mensuelle doit se produire le 31 mars");
    }

    @Test
    public void longDurationEventSpansMultipleDays() {
        // durée 72 heures -> 3 jours
        LocalDateTime start = LocalDateTime.of(2020, 11, 1, 10, 0);
        Event e = new Event("Long event", start, Duration.ofHours(72));
        // doit couvrir J, J+1 et J+2 mais pas J+3
        assertTrue(e.isInDay(LocalDate.of(2020,11,1)), "Devrait couvrir le jour de départ");
        assertTrue(e.isInDay(LocalDate.of(2020,11,2)), "Devrait couvrir J+1");
        assertTrue(e.isInDay(LocalDate.of(2020,11,3)), "Devrait couvrir J+2");
        assertFalse(e.isInDay(LocalDate.of(2020,11,4)), "Ne doit pas couvrir J+3");
    }
}
