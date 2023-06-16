package nl.softcause.onestoplogshop.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class InstantDeserializer extends JsonDeserializer<Instant> {

    private final DateTimeFormatter formatter;

    public InstantDeserializer() {
        super();
        this.formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .withLocale(Locale.getDefault())
                .withZone(ZoneOffset.UTC);
    }

    public Instant parse(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return formatter.parse(value, Instant::from);
    }

    @Override
    public Instant deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        var node = p.getCodec().readTree(p);
        if (node.isObject()) {
            node = node.get("value");
        }
        var value = ((TextNode) node).textValue();
        return parse(value);
    }

    @Override
    public Class<Instant> handledType() {
        return Instant.class;
    }
}
