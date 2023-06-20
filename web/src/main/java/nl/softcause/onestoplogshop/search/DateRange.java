package nl.softcause.onestoplogshop.search;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import nl.softcause.onestoplogshop.util.InstantDeserializer;
import nl.softcause.onestoplogshop.util.InstantSerializer;

public class DateRange {

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant min;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant max;

    public static boolean isEmpty(final DateRange at) {
        if (at == null) {
            return true;
        }
        return at.getMin() == null && at.getMax() == null;
    }

    public Instant getMin() {
        return min;
    }

    public void setMin(Instant min) {
        this.min = min;
    }

    public Instant getMax() {
        return max;
    }

    public void setMax(Instant max) {
        this.max = max;
    }
}
