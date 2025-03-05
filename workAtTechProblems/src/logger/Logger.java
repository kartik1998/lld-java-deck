package logger;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class Logger {
    protected LogLevel logLevel;
    protected Logger nextLogger;
    private ExecutorService executorService = Executors.newFixedThreadPool(3);

    public Logger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public void setNextLogger(Logger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void log(LogLevel logLevel, String message) {
        if (this.logLevel.ordinal() <= logLevel.ordinal()) {
            write(formatLog(logLevel, message));
        }
        if (this.nextLogger != null) {
            this.nextLogger.log(logLevel, message);
        }
    }

    private String formatLog(LogLevel logLevel, String message) {
        return String.format("[%s] [%s] %s", new Date().toGMTString(), logLevel, message);
    }

    public abstract void write(String message);
}
