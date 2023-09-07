package nl.softcause.onestoplogshop.connector;

import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlainSocketListener {
    private static final Logger logger = LoggerFactory.getLogger(PlainSocketListener.class);

    public PlainSocketListener(int socketPort, LoggingEventPublisher publisher) {
        logger.info("Starting plain socket listener");
        var factory = new PlainSocketClientFactory(socketPort, publisher);
        var thread = new Thread(factory);
        thread.setDaemon(true);
        thread.start();
    }
}
