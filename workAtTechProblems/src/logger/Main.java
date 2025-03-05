package logger;

/**
 * Loggers use the Chain of responsibility design pattern.
 * Sample Console -> File -> Database
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLoggerChain();
        logger.log(LogLevel.DEBUG, "This is a test");
    }
}
