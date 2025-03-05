package logger;

public class FileLogger extends Logger{
    public FileLogger(LogLevel logLevel) {
        super(logLevel);
    }

    @Override
    public synchronized void write(String message) {
        System.out.println("[File] " + message);
    }
}
