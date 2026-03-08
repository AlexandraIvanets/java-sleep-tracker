package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepQuality;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public class BadQualitySessionCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final String title = "Количество сессий с плохим качеством сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult(
                title,
                Math.toIntExact(sleepingSessions.stream()
                        .filter(sleepingSession -> sleepingSession.getSleepQuality() == SleepQuality.BAD)
                        .count())
        );
    }
}
