package nl.softcause.onestoplogshop.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import java.time.Instant;
import nl.softcause.onestoplogshop.util.InstantDeserializer;
import nl.softcause.onestoplogshop.util.InstantSerializer;
import org.springframework.stereotype.Component;

@SuppressWarnings("unused")
@Component
public class JacksonModule extends SimpleModule {

    public JacksonModule() {
        this.addDeserializer(Instant.class, new InstantDeserializer());
        this.addSerializer(Instant.class, new InstantSerializer());

    }
}
