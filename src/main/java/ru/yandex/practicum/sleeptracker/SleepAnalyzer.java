package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.functions.*;

import java.util.List;
import java.util.function.Function;

public class SleepAnalyzer {
    private SleepAnalyzer() {
    }

    private static final List<Function<List<SleepingSession>, SleepAnalysisResult>> functions = List.of(
            new SleepSessionCounter(),
            new MinSleepDurationCalculator(),
            new MaxSleepDurationCalculator(),
            new AvgSleepDurationCalculator(),
            new BadQualitySessionCounter(),
            new SleeplessNightsCounter(),
            new ChronotypeDeterminer()
    );

    public static List<SleepAnalysisResult> analyze(List<SleepingSession> sessions) {
        if (sessions.isEmpty()) {
            return List.of();
        }

        return functions.stream()
                .map(function -> function.apply(sessions))
                .toList();
    }
}
