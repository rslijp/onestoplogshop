package nl.softcause.onestoplogshop.connector;

import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLLogbackSocketListener {
    private static final Logger logger = LoggerFactory.getLogger(SSLLogbackSocketListener.class);

    public SSLLogbackSocketListener(int socketPort, LoggingEventPublisher publisher, SSLLogbackContext context) {
        logger.info("Starting logback ssl socket listener");
        var factory = new SSLLogbackSocketClientFactory(socketPort, publisher, context);
        var thread = new Thread(factory);
        thread.setDaemon(true);
        thread.start();
    }
}
