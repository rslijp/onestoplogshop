package nl.softcause.onestoplogshop.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackSocketClientFactory implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(LogbackSocketClientFactory.class);

    private final int socketPort;
    private final LoggingEventPublisher publisher;
    private ServerSocket server;

    // Constructor
    public LogbackSocketClientFactory(int socketPort, LoggingEventPublisher publisher)
    {
        this.socketPort = socketPort;
        this.publisher = publisher;
    }

    public void run()
    {
        logger.info("Creating listener on port "+socketPort);
        try {

            // server is listening on port 1234
            this.server = new ServerSocket(socketPort);
            this.server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                logger.debug("New client connected {}",client.getInetAddress().getHostAddress());

                // create a new thread object
                var clientSock
                        = new ObjectClientHandler(client, publisher);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}