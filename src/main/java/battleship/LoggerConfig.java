package battleship;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {
    public static void configureLogger() {
        Logger rootLogger = Logger.getLogger(""); // Root-Logger
        for (var handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setFormatter(new SimpleFormatter() {
                    @Override
                    public String format(java.util.logging.LogRecord record) {
                        // Nur die Nachricht ohne Zeitstempel oder Log-Level
                        return record.getMessage() + System.lineSeparator();
                    }
                });
            }
        }
        rootLogger.setLevel(Level.INFO); // Setzt das Log-Level (optional)
    }
}
