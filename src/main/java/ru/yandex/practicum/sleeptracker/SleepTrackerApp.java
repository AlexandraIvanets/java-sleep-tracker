package ru.yandex.practicum.sleeptracker;

import java.io.IOException;
import java.util.List;

public class SleepTrackerApp {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Передайте путь к файлу с логом сна в качестве аргумента командной строки ");
            return;
        }

        try {
            List<SleepingSession> sessions = SleepLogReader.readFromFile(args[0]);
            SleepAnalyzer
                    .analyze(sessions)
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Ошибка при попытке прочитать лог файл: " + e.getMessage());
        }
    }
}