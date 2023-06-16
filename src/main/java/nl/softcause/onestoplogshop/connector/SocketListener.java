package nl.softcause.onestoplogshop.connector;

import java.net.ServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketListener {
    private static final Logger logger = LoggerFactory.getLogger(SocketListener.class);

    public SocketListener(int socketPort) {
        logger.info("Starting socket client factory");
        var factory = new SocketClientFactory(socketPort);
        var thread = new Thread(factory);
        thread.setDaemon(true);
        thread.start();
    }
}
