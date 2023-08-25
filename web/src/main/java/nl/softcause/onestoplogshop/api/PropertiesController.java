package nl.softcause.onestoplogshop.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.List;
import nl.softcause.onestoplogshop.config.LoggingProperties;
import nl.softcause.onestoplogshop.model.LogginEventRepository;
import nl.softcause.onestoplogshop.util.InstantSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PropertiesController {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesController.class);
    private final LoggingProperties properties;
    private final LogginEventRepository logginEventRepository;

    public PropertiesController(LoggingProperties properties, LogginEventRepository logginEventRepository){
        this.properties=properties;
        this.logginEventRepository=logginEventRepository;
    }

    @GetMapping(path = "/api/properties")
    public @ResponseBody
    LoggingProperties retrieve() {
        logger.debug("Api properties call");
        return properties;
    }

    @GetMapping(path = "/api/property-values/{field}")
    public @ResponseBody
    List<String> retrieve(@PathVariable String field) {
        logger.debug("Api property values call");
        return logginEventRepository.findFieldValues(field);
    }
}
