package nl.softcause.onestoplogshop.events;

import nl.softcause.onestoplogshop.model.LogginEventRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class LoggingEventListener  {

    private final LogginEventRepository repository;

    public LoggingEventListener(LogginEventRepository repository) {
        this.repository = repository;
    }

    @Async
    @Transactional
    @EventListener
    public void onApplicationEvent(OnLoggingEvent event) {
        repository.save(event.getMessage());
    }

}