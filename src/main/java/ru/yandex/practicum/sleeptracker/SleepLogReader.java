package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

public class SleepLogReader {
    private SleepLogReader() {
    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    public static List<SleepingSession> readFromFile(String fileName) throws IOException {
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {

            return lines.map(String::trim)
                    .map(SleepLogReader::parseLine)
                    .toList();
        }
    }

    private static SleepingSession parseLine(String line) {
        String[] parts = line.split(";");
        return new SleepingSession(
                LocalDateTime.parse(parts[0].trim(), FORMATTER),
                LocalDateTime.parse(parts[1].trim(), FORMATTER),
                SleepQuality.valueOf(parts[2].trim().toUpperCase()));
    }
}