package nl.softcause.onestoplogshop.connector;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("socket-listener")
@Configuration
public class SocketListenerConfiguration {

    @Value("${listener.socket.port:9000}")
    private int socketPort;

    @Bean
    public SocketListener listener() throws IOException {
        return new SocketListener(socketPort);
    }
}
