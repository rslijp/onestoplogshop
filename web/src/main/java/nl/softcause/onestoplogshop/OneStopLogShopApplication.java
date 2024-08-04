package nl.softcause.onestoplogshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories
@EnableAsync(proxyTargetClass = true)
public class OneStopLogShopApplication {
    private static final Logger logger = LoggerFactory.getLogger(OneStopLogShopApplication.class);

    public OneStopLogShopApplication(@Value(value = "${info.version}") String version,
                                     @Value(value = "${info.nodeId}") String nodeId,
                                     @Value(value = "${spring.application.name}") String applicationName) {
        MDC.put("version", version);
        MDC.put("nodeId", nodeId);
        MDC.put("applicationName", applicationName);
        logger.info("Booting application");
    }

    public static void main(String[] args) {
        SpringApplication.run(OneStopLogShopApplication.class, args);
    }

}
