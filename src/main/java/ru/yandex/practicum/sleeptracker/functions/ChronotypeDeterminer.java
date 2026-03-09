package ru.yandex.practicum.sleeptracker.functions;

import ru.yandex.practicum.sleeptracker.Chronotype;
import ru.yandex.practicum.sleeptracker.SleepAnalysisResult;
import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ChronotypeDeterminer implements Function<List<SleepingSession>, SleepAnalysisResult> {
    private final String title = "Хронотип на основе сессий сна";

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sleepingSessions) {

        Map<LocalDate, List<SleepingSession>> dataToSession = sleepingSessions.stream()
                .filter(this::isNightSession)
                .collect(Collectors.toMap(
                                session -> session.getWakeUpAt().toLocalDate(),
                                session -> new ArrayList<>(List.of(session)),
                                (oldValue, newValue) -> {
                                    oldValue.addAll(newValue);
                                    return oldValue;
                                }
                        )
                );

        //если сессий больше 1 в день - голубь, если 1 - подходящее животное
        Chronotype chronotype = dataToSession.entrySet().stream()
                .map(entry -> entry.getValue().size() > 1 ? Chronotype.DOVE : determineChronotype(entry.getValue().getFirst()))
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.<Chronotype, Long>comparingByValue()
                        .thenComparing(e -> e.getKey() == Chronotype.DOVE ? 1 : 0))
                .map(Map.Entry::getKey)
                .orElse(Chronotype.DOVE);

        return new SleepAnalysisResult(title, chronotype);
    }

    private boolean isNightSession(SleepingSession session) {
        return session.getFallAsleepAt().toLocalDate().plusDays(1)
                .equals(session.getWakeUpAt().toLocalDate())
                || session.getFallAsleepAt().getHour() < 6;
    }

    private Chronotype determineChronotype(SleepingSession session) {
        int fallAsleepHour = session.getFallAsleepAt().getHour();
        int wakeUpHour = session.getWakeUpAt().getHour();

        if ((fallAsleepHour == 23 || fallAsleepHour < 9) && wakeUpHour >= 9) {
            return Chronotype.OWL;
        } else if ((fallAsleepHour < 22 && fallAsleepHour > 7) && wakeUpHour < 7) {
            return Chronotype.LARK;
        } else {
            return Chronotype.DOVE;
        }
    }
}
