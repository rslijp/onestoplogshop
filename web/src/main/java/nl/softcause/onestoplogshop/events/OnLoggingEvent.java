package nl.softcause.onestoplogshop.events;

import nl.softcause.onestoplogshop.model.LoggingEvent;
import org.springframework.context.ApplicationEvent;

public class OnLoggingEvent extends ApplicationEvent {
    private LoggingEvent message;

    public OnLoggingEvent(Object source, LoggingEvent message) {
        super(source);
        this.message = message;
    }
    public LoggingEvent getMessage() {
        return message;
    }
}