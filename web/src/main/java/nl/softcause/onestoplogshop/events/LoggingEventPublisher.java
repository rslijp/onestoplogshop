package nl.softcause.onestoplogshop.events;

import nl.softcause.onestoplogshop.model.LoggingEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;


@Component
public class LoggingEventPublisher {

    private ApplicationEventPublisher applicationEventPublisher;

    public LoggingEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final LoggingEvent message) {
        OnLoggingEvent customSpringEvent = new OnLoggingEvent(this, message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }
}