package nl.softcause.onestoplogshop.connector;

import ch.qos.logback.classic.spi.LoggingEventVO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import nl.softcause.onestoplogshop.model.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class ObjectClientHandler implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PlainSocketListener.class);
    private static int COUNTER=1;

    private final int id;
    private final Socket clientSocket;
    private final LoggingEventPublisher publisher;
    private ObjectInputStream reader;

    public ObjectClientHandler(Socket socket, LoggingEventPublisher publisher) {
        this.clientSocket = socket;
        this.publisher = publisher;
        id=COUNTER++;
    }

    public void run() {
//        HERE
        try (var reader = new ObjectInputStream(
                this.clientSocket.getInputStream())) {
            this.reader = reader;
            LoggingEventVO line;
            while ((line = (LoggingEventVO) reader.readObject())!=null) {
                publisher.publishEvent(LoggingEvent.fromVo(line));
            }
        } catch (IOException | ClassNotFoundException e) {
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