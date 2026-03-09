package ru.yandex.practicum.sleeptracker.functions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDateTime;
import java.util.List;

class MaxSleepDurationCalculatorTest {
    static MaxSleepDurationCalculator calculator;

    @BeforeAll
    static void setUp() {
        calculator = new MaxSleepDurationCalculator();
    }

    @Test
    void shouldReturnMaxDuration() {
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

        int expected = 510;
        int result = (int) calculator.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnDurationForSingleSession() {
        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(
                        LocalDateTime.of(2026, 1, 10, 23, 0),
                        LocalDateTime.of(2026, 1, 11, 7, 30),
                        SleepQuality.GOOD
                )
        );

        int expected = 510;
        int result = (int) calculator.apply(sleepingSessions).result();

        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldThrowExceptionForEmptyList() {
        List<SleepingSession> sleepingSessions = List.of();

        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.apply(sleepingSessions));
    }
}