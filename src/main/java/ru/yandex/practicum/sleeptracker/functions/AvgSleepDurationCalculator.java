package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class AvgSleepDurationCalculator implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final String title = "Средняя продолжительность сессии (в минутах)";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult(
                title,
                (int) Math.round(sleepingSessions.stream()
                        .mapToInt(SleepingSession::getDurationInMinutes)
                        .average()
                        .orElseThrow(() -> new IllegalArgumentException("Невозможно вычислить среднюю " +
                                "продолжительность сна"))));
    }
}