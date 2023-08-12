package nl.softcause.onestoplogshop.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.helpers.BasicMarker;
import org.slf4j.helpers.BasicMarkerFactory;

public class TextClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(SocketListener.class);

    private final Socket clientSocket;
    private BufferedReader reader;

    public TextClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        MDC.put("nodeId", clientSocket.getInetAddress().getHostName());
        MDC.put("applicationName", "socketListener");
        MDC.put("version", "-");
        try (var reader = new BufferedReader(
                new InputStreamReader
                        (this.clientSocket.getInputStream(), StandardCharsets.UTF_8))) {
            this.reader = reader;
            String line;
            while ((line = reader.readLine())!=null) {
                logger.info(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            MDC.clear();
        }
    }
}