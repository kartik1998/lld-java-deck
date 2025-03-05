package logger;

public class DBLogger extends Logger {
    public DBLogger(LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    public void write(String message) {
        System.out.println("[DatabaseLog] " + message);
    }
}
