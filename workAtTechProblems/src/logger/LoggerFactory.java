package logger;

public class LoggerFactory {
    public static Logger getLoggerChain() {
        Logger consoleLogger = new ConsoleLogger(LogLevel.DEBUG);
        Logger fileLogger = new FileLogger(LogLevel.DEBUG);
        Logger dbLogger = new DBLogger(LogLevel.DEBUG);

        consoleLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(dbLogger);
        return consoleLogger;
    }
}
