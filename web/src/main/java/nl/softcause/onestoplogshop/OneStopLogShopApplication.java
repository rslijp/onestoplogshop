package nl.softcause.onestoplogshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OneStopLogShopApplication {
    private static final Logger logger = LoggerFactory.getLogger(OneStopLogShopApplication.class);
    private final String version;
    private final String nodeId;
    private final String applicationName;

    public OneStopLogShopApplication(@Value(value = "${info.version}") String version,
                                     @Value(value = "${info.nodeId}") String nodeId,
                                     @Value(value = "${spring.application.name}") String applicationName) {
        this.version = version;
        this.nodeId = nodeId;
        this.applicationName = applicationName;
        MDC.put("version", version);
        MDC.put("nodeId", nodeId);
        MDC.put("applicationName", applicationName);
        logger.info("Booting application");
    }

    public static void main(String[] args) {
        SpringApplication.run(OneStopLogShopApplication.class, args);
    }

}
