package nl.softcause.onestoplogshop.connector;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketClientFactory implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SocketClientFactory.class);

    private final int socketPort;
    private ServerSocket server;

    // Constructor
    public SocketClientFactory(int socketPort)
    {
        this.socketPort = socketPort;
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
                        = new TextClientHandler(client);

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