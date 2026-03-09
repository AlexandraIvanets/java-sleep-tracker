package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepLogReader;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

class SleeplessNightsCounterTest {
    static SleeplessNightsCounter counter;

    @BeforeAll
    static void setUp() {
        counter = new SleeplessNightsCounter();
    }

    @Test
    void shouldReturnRightNumberForClassicNights() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 22, 30),
                        LocalDateTime.of(2026, 1, 2, 5, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 4, 23, 30),
                        LocalDateTime.of(2026, 1, 5, 9, 0),
                        SleepQuality.NORMAL
                )
        );

        int expected = 2;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfFirstSessionStartsAfterTwelveAM() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 15, 0),
                        LocalDateTime.of(2026, 1, 1, 18, 50),
                        SleepQuality.GOOD
                )
        );

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfFirstSessionStartsOnTwelveAM() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 12, 0),
                        LocalDateTime.of(2026, 1, 1, 18, 50),
                        SleepQuality.GOOD
                )
        );

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfFirstSessionStartsBeforeTwelveAMButAfterSixAM() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 11, 0),
                        LocalDateTime.of(2026, 1, 1, 18, 50),
                        SleepQuality.GOOD
                )
        );

        int expected = 1;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfFirstSessionStartsOnSixAM() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 6, 0),
                        LocalDateTime.of(2026, 1, 1, 18, 50),
                        SleepQuality.GOOD
                )
        );

        int expected = 1;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfFirstSessionStartsBeforeSixAM() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 5, 0),
                        LocalDateTime.of(2026, 1, 1, 18, 50),
                        SleepQuality.GOOD
                )
        );

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfSessionStartsInOneMonthAndFinalsInTheAnother() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 14, 22, 0),
                        LocalDateTime.of(2026, 1, 15, 8, 50),
                        SleepQuality.GOOD
                ),

                new SleepingSession(
                        LocalDateTime.of(2026, 3, 1, 22, 0),
                        LocalDateTime.of(2026, 3, 2, 8, 50),
                        SleepQuality.GOOD
                )
        );

        int expected = 45;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfSessionStartsInOneYearAndFinalsInTheAnother() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2025, 12, 30, 22, 0),
                        LocalDateTime.of(2025, 12, 31, 8, 50),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2025, 12, 31, 22, 0),
                        LocalDateTime.of(2026, 1, 1, 8, 50),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 22, 30),
                        LocalDateTime.of(2026, 1, 2, 5, 30),
                        SleepQuality.GOOD
                )
        );

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberIfThereAreSeveralSessionsPerDay() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 1, 22, 30),
                        LocalDateTime.of(2026, 1, 2, 4, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 5, 30),
                        LocalDateTime.of(2026, 1, 2, 6, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 2, 8, 30),
                        LocalDateTime.of(2026, 1, 2, 12, 30),
                        SleepQuality.GOOD
                )
        );

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnRightNumberForTestSleepLog() throws IOException {
        List<SleepingSession> sleepingSessions = SleepLogReader.readFromFile("src/test/resources/test_sleep_log.txt");

        int expected = 20;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }
}
