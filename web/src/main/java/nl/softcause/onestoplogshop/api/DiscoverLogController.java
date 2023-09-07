
package nl.softcause.onestoplogshop.api;

import java.time.Instant;
import java.util.List;
import nl.softcause.onestoplogshop.model.LoggingEvent;
import nl.softcause.onestoplogshop.model.LogginEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoverLogController {

    private static final Logger logger = LoggerFactory.getLogger(DiscoverLogController.class);private static final Instant startedAt = Instant.now();
    private final LogginEventRepository repository;

    public DiscoverLogController(LogginEventRepository repository){
        this.repository=repository;
    }



    List<LoggingEvent> retrieveBatchFrom(LogEventSearchRequest request, Long id){
        logger.debug("Returning data from id {}", id!=null?id.toString():"-");
        var search = request==null?new LogEventSearchRequest():request;
        search.setFromId(id);
        var result = repository.findAll(search.getSpecification(), search.asPageable()).getContent();
        logger.debug("Found "+result.size()+" hits");
        return result;
//        return repository.findFirst20WithIdBeforeOrderDescById(id!=null?id:Long.MAX_VALUE);
    }

    @PostMapping(path = "/api/discover-logs")
    public @ResponseBody
    List<LoggingEvent> retrieve(@RequestBody LogEventSearchRequest request) {
        logger.debug("Initial query");
        return retrieveBatchFrom(request,null);
    }

    @PostMapping(path = "/api/discover-logs/{id}")
    public @ResponseBody
    List<LoggingEvent> retrieve(@RequestBody LogEventSearchRequest request, @PathVariable Long id) {
        logger.debug("Continuation query from {}", id);
        return retrieveBatchFrom(request, id);
    }

}
