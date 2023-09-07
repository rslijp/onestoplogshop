package nl.softcause.onestoplogshop.connector;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLLogbackSocketClientFactory implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SSLLogbackSocketClientFactory.class);

    private final int socketPort;
    private final LoggingEventPublisher publisher;
    private final SSLLogbackContext context;
    private ServerSocket server;

    // Constructor
    public SSLLogbackSocketClientFactory(int socketPort, LoggingEventPublisher publisher, SSLLogbackContext context)
    {
        this.socketPort = socketPort;
        this.publisher = publisher;
        this.context = context;
    }

    public void run()
    {
        logger.info("Creating ssl listener on port "+socketPort);
        try {
            var ssf = context.getSocketFactory();
            this.server = ssf.createServerSocket(socketPort);


            // server is listening on port 1234
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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