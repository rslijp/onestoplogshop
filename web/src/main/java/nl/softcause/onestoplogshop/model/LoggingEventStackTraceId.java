package nl.softcause.onestoplogshop.model;

import java.io.Serializable;
import java.util.Objects;

public class LoggingEventStackTraceId implements Serializable {
    private Long i;

    private LoggingEvent event;

    public LoggingEventStackTraceId(){}

    public LoggingEventStackTraceId(Long i, LoggingEvent event) {
        this.i = i;
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoggingEventStackTraceId that = (LoggingEventStackTraceId) o;
        return i.equals(that.i) && event.equals(that.event);
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, event);
    }
}
