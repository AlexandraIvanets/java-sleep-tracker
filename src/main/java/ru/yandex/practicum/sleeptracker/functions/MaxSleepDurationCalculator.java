package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class MaxSleepDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final String title = "Максимальная продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult(
                title,
                sleepingSessions.stream()
                        .max(Comparator.comparingInt(SleepingSession::getDurationInMinutes))
                        .orElseThrow(() -> new IllegalArgumentException("Невозможно вычислить максимальную " +
                                "продолжительность сна"))
                        .getDurationInMinutes());
    }
}

