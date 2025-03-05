package logger;

/**
 * Loggers use the Chain of responsibility design pattern.
 * Sample Console -> File -> Database
 *
 * Other similar problems are ATM machine and Vending machine
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLoggerChain();
        logger.log(LogLevel.DEBUG, "This is a test");
    }
}
