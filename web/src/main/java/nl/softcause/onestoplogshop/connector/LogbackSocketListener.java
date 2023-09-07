package nl.softcause.onestoplogshop.connector;

import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackSocketListener {
    private static final Logger logger = LoggerFactory.getLogger(LogbackSocketListener.class);

    public LogbackSocketListener(int socketPort, LoggingEventPublisher publisher) {
        logger.info("Starting logback socket listener");
        var factory = new LogbackSocketClientFactory(socketPort, publisher);
        var thread = new Thread(factory);
        thread.setDaemon(true);
        thread.start();
    }
}
