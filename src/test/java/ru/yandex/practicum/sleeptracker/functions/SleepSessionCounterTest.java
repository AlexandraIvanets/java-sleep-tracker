package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

class SleepSessionCounterTest {
    static SleepSessionCounter sleepSessionCounter;
    static List<SleepingSession> sleepingSessions;

    @BeforeAll
    static void setUp() {
        sleepSessionCounter = new SleepSessionCounter();
        sleepingSessions = List.of(
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
    }

    @Test
    void shouldReturnCorrectCount() {
        int expected = sleepingSessions.size();
        int result = (int) sleepSessionCounter.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        List<SleepingSession> emptySessions = List.of();

        int expected = 0;
        int result = (int) sleepSessionCounter.apply(emptySessions).result();

        Assertions.assertEquals(expected, result);
    }
}