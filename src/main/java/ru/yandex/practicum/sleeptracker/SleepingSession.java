package ru.yandex.practicum.sleeptracker;

import java.time.Duration;
import java.time.LocalDateTime;

public class SleepingSession {
    private final LocalDateTime fallAsleepAt;
    private final LocalDateTime wakeUpAt;
    private final SleepQuality sleepQuality;

    public SleepingSession(LocalDateTime fallAsleepAt, LocalDateTime wakeUpAt, SleepQuality sleepQuality) {
        this.fallAsleepAt = fallAsleepAt;
        this.wakeUpAt = wakeUpAt;
        this.sleepQuality = sleepQuality;
    }

    public int getDurationInMinutes() {
        return Math.toIntExact(Duration.between(fallAsleepAt, wakeUpAt).toMinutes());
    }

    public LocalDateTime getFallAsleepAt() {
        return fallAsleepAt;
    }

    public LocalDateTime getWakeUpAt() {
        return wakeUpAt;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}