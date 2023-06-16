package nl.softcause.onestoplogshop.api;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import java.time.Instant;
import javax.servlet.http.HttpServletResponse;
import nl.softcause.onestoplogshop.OneStopLogShopApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private static final Logger logger = LoggerFactory.getLogger(VersionController.class);
    private static final Instant startedAt = Instant.now();
    private final String version;
    private final String nodeId;

    public VersionController(@Value("${info.version}") String version,
                             @Value("${info.nodeId}") String nodeId){
        this.version=version;
        this.nodeId=nodeId;
    }

    @GetMapping(path = "/api/version")
    public @ResponseBody
    VersionStatus retrieve() {
        logger.info("Api version call");
        return new VersionStatus(startedAt, version, nodeId);
    }

    public static class VersionStatus {

        private final Instant startedAt;

        private final String version;

        private final String nodeId;

        public VersionStatus(Instant startedAt, String version, String nodeId) {
            this.startedAt = startedAt;
            this.version = version;
            this.nodeId = nodeId;
        }

        @JsonSerialize(using = InstantSerializer.class)
        public Instant getStartedAt() {
            return startedAt;
        }

        public String getVersion() {
            return version;
        }

        public String getNodeId() {
            return nodeId;
        }
    }
}
