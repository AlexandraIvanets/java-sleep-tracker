package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepLogReader;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

class ChronotypeDeterminerTest {
    static ChronotypeDeterminer chronotypeDeterminer;

    @BeforeAll
    public static void setUp() {
        chronotypeDeterminer = new ChronotypeDeterminer();
    }

    @Test
    void shouldReturnOwlWhenNightStartedAfter23AndFinishedAfter9() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 23, 30),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.OWL, chronotype);
    }

    @Test
    void shouldReturnOwlWhenNightStartedAfter00AndFinishedAfter9() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 5, 59),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.OWL, chronotype);
    }

    @Test
    void shouldReturnLarkWhenNightStartedBefore22AndFinishedBefore7() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 21, 0),
                        LocalDateTime.of(2026, 1, 2, 6, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.LARK, chronotype);
    }

    @Test
    void shouldReturnLarkWhenNightStartedAfternoonaAndFinishedBefore7() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 17, 0),
                        LocalDateTime.of(2026, 1, 2, 3, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.LARK, chronotype);
    }

    @Test
    void shouldReturnDoveWhenNightStartedBefore23AndFinishedAfter9() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 22, 59),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.DOVE, chronotype);
    }

    @Test
    void shouldReturnDoveWhenNightStartedAfter22AndFinishedBefore9() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 22, 1),
                        LocalDateTime.of(2026, 1, 2, 8, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.DOVE, chronotype);
    }

    @Test
    void shouldReturnDoveWhenThereAreMoreThanOneSessionPerOneNight() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 2, 1),
                        LocalDateTime.of(2026, 1, 2, 3, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 4, 1),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ));
        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.DOVE, chronotype);
    }

    @Test
    void shouldReturnOwlWhenAmountOfOwlsNightsMore() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 23, 30),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 3, 5, 59),
                        LocalDateTime.of(2026, 1, 3, 10, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 3, 17, 0),
                        LocalDateTime.of(2026, 1, 4, 3, 30),
                        SleepQuality.GOOD
                ));

        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.OWL, chronotype);
    }

    @Test
    void shouldReturnLarkWhenAmountOfLarkNightsMore() {
        List<SleepingSession> sleepingSessions = List.of(

                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 23, 30),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 21, 0),
                        LocalDateTime.of(2026, 1, 3, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 3, 17, 0),
                        LocalDateTime.of(2026, 1, 4, 3, 30),
                        SleepQuality.GOOD
                ));

        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.LARK, chronotype);
    }

    @Test
    void shouldReturnDoveWhenAmountOfDoveNightsMore() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 23, 30),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 21, 0),
                        LocalDateTime.of(2026, 1, 3, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 3, 17, 0),
                        LocalDateTime.of(2026, 1, 4, 3, 30),
                        SleepQuality.GOOD
                ),

                new SleepingSession(
                        LocalDateTime.of(2026, 1, 4, 22, 59),
                        LocalDateTime.of(2026, 1, 5, 10, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 5, 22, 1),
                        LocalDateTime.of(2026, 1, 6, 8, 30),
                        SleepQuality.GOOD
                ));

        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.DOVE, chronotype);
    }

    @Test
    void shouldReturnDoveWhenThereTwoAcceptableChronotype() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 23, 30),
                        LocalDateTime.of(2026, 1, 2, 10, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 21, 0),
                        LocalDateTime.of(2026, 1, 2, 6, 30),
                        SleepQuality.GOOD
                ));

        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();
        Assertions.assertEquals(Chronotype.DOVE, chronotype);
    }

    @Test
    void shouldReturnRightChronotypeForTestSleepLog() throws IOException {
        List<SleepingSession> sleepingSessions = SleepLogReader.readFromFile("src/test/resources/test_sleep_log.txt");

        Chronotype chronotype = (Chronotype) chronotypeDeterminer.apply(sleepingSessions).result();

        Assertions.assertEquals(Chronotype.LARK, chronotype);
    }
}