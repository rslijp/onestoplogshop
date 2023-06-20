
package nl.softcause.onestoplogshop.api;

import java.time.Instant;
import java.util.List;
import nl.softcause.onestoplogshop.model.LogginEvent;
import nl.softcause.onestoplogshop.model.LogginEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiscoverLogController {

    private static final Logger logger = LoggerFactory.getLogger(DiscoverLogController.class);private static final Instant startedAt = Instant.now();
    private final LogginEventRepository repository;

    public DiscoverLogController(LogginEventRepository repository){
        this.repository=repository;
    }



    List<LogginEvent> retrieveBatchFrom(Long id){
        logger.info("Returning data from id {}", id!=null?id.toString():"-");
        var search = new LogeEventSearchRequest();
        search.setFromId(id);
        var result = repository.findAll(search.getSpecification(), search.asPageable()).getContent();
        logger.info("Found "+result.size()+" hits");
        return result;
//        return repository.findFirst20WithIdBeforeOrderDescById(id!=null?id:Long.MAX_VALUE);
    }

    @GetMapping(path = "/api/discover-logs")
    public @ResponseBody
    List<LogginEvent> retrieve() {
        logger.info("Initial query");
        return retrieveBatchFrom(null);
    }

    @GetMapping(path = "/api/discover-logs/{id}")
    public @ResponseBody
    List<LogginEvent> retrieve(@PathVariable Long id) {
        logger.info("Continuation query from {}", id);
        return retrieveBatchFrom(id);
    }

}