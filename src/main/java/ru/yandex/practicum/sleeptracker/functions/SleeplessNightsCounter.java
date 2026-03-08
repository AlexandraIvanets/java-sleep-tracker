package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleeplessNightsCounter implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final String title = "Общее количество бессонных ночей";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {
        SleepingSession firstSession = sleepingSessions.getFirst();

        int amountOfAllNights = (int) ChronoUnit.DAYS.between(
                firstSession.getFallAsleepAt().toLocalDate(),
                sleepingSessions.getLast().getWakeUpAt().toLocalDate()
        );

        if (firstSession.getFallAsleepAt().getHour() < 12) {
            amountOfAllNights++;
        }

        Set<LocalDate> sleepingNights = sleepingSessions.stream()
                .filter(this::isNightSession)
                .map(session -> session.getWakeUpAt().toLocalDate())
                .collect(Collectors.toSet());

        int sleeplessNights = amountOfAllNights - sleepingNights.size();

        return new SleepAnalysisResult(title, sleeplessNights);
    }

    private boolean isNightSession(SleepingSession session) {
        return session.getFallAsleepAt().toLocalDate().plusDays(1)
                .equals(session.getWakeUpAt().toLocalDate())
                || session.getFallAsleepAt().getHour() < 6;
    }
}
