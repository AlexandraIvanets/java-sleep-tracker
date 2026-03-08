package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

class BadQualitySessionCounterTest {
    static BadQualitySessionCounter counter;

    @BeforeAll
    static void setUp() {
        counter = new BadQualitySessionCounter();
    }

    @Test
    void shouldReturnCorrectBadCount() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 10, 23, 0),
                        LocalDateTime.of(2026, 1, 11, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 11, 22, 30),
                        LocalDateTime.of(2026, 1, 12, 6, 0),
                        SleepQuality.NORMAL
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 13, 1, 0),
                        LocalDateTime.of(2026, 1, 13, 5, 30),
                        SleepQuality.BAD
                )
        );

        int expected = 1;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnZeroWhenNoBadSessions() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 10, 23, 0),
                        LocalDateTime.of(2026, 1, 11, 7, 30),
                        SleepQuality.GOOD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 11, 22, 30),
                        LocalDateTime.of(2026, 1, 12, 6, 0),
                        SleepQuality.NORMAL
                )
        );

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> sleepingSessions = List.of();

        int expected = 0;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldCountMultipleBadSessions() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 10, 23, 0),
                        LocalDateTime.of(2026, 1, 11, 7, 30),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 11, 22, 30),
                        LocalDateTime.of(2026, 1, 12, 6, 0),
                        SleepQuality.BAD
                ),
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 13, 1, 0),
                        LocalDateTime.of(2026, 1, 13, 5, 30),
                        SleepQuality.BAD
                )
        );

        int expected = 3;
        int result = (int) counter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }
}