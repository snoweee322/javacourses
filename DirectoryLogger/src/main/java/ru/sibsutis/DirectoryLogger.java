package ru.sibsutis;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DirectoryLogger {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path loggerFile = Path.of("DirectoryLogger.log");
        Files.deleteIfExists(loggerFile); // existing file will be overwritten
        Files.write(loggerFile, "".getBytes()); // "creating file"
        WatchService watcher = FileSystems.getDefault().newWatchService();
        WatchKey key = Path.of("C:\\Windows\\Temp").register(watcher, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE); // the path may vary
        while ((key = watcher.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(event.context());
                System.out.println(event.kind().name());
                String temp = "Time: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME)
                        + " File: " + event.context() +
                        " Event: " + event.kind().name() + "\n";
                Files.writeString(loggerFile, temp, StandardOpenOption.APPEND);
                key.reset();
            }
        }
    }
}
