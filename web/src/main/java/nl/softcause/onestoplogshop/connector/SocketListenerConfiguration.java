package nl.softcause.onestoplogshop.connector;

import java.io.IOException;
import nl.softcause.onestoplogshop.events.LoggingEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class SocketListenerConfiguration {

    @Value("${listener.socket.port.plain:9000}")
    private int plainSocketPort;

    @Value("${listener.socket.port.logback:6000}")
    private int logbackSocketPort;

    @Value("${listener.socket.port.ssllogback:6001}")
    private int sslLogbackSocketPort;

    @Bean
    public PlainSocketListener plainListener(LoggingEventPublisher publisher) throws IOException {
        return new PlainSocketListener(plainSocketPort, publisher);
    }

    @Bean
    public LogbackSocketListener logbackListener(LoggingEventPublisher publisher) throws IOException {
        return new LogbackSocketListener(logbackSocketPort, publisher);
    }

    @Bean
    public SSLLogbackSocketListener sslLogbackListener(LoggingEventPublisher publisher, SSLLogbackContext context) throws IOException {
        return new SSLLogbackSocketListener(sslLogbackSocketPort, publisher, context);
    }
}
