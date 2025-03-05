package logger;

public class ConsoleLogger extends Logger {
    public ConsoleLogger(LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    public void write(String message) {
        System.out.println(String.format("[Console] %s", message));
    }
}
