package nl.softcause.onestoplogshop.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import nl.softcause.onestoplogshop.model.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlainSocketListener.class);

    private final Socket clientSocket;
    private final LoggingEventPublisher publisher;
    private BufferedReader reader;

    public TextClientHandler(Socket socket, LoggingEventPublisher publisher) {
        this.clientSocket = socket;
        this.publisher = publisher;
    }

    public void run() {
        var nodeId =  clientSocket.getInetAddress().getHostName();


        try (var reader = new BufferedReader(
                new InputStreamReader
                        (this.clientSocket.getInputStream(), StandardCharsets.UTF_8))) {
            this.reader = reader;
            String line;
            while ((line = reader.readLine())!=null) {
                publisher.publishEvent(buildMessage(nodeId, line));
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
        }
    }

    private LoggingEvent buildMessage(String nodeId, String line) {
        var loggingEvent = LoggingEvent.fromText(line, "socketListener", Map.of(
                "nodeId", nodeId,
                "applicationName", "socketListener"));
        return loggingEvent;
    }
}