package nl.softcause.onestoplogshop;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public class SampleClient {

    private static final Logger logger
            = LoggerFactory.getLogger(SampleClient.class);

    @Test
    public void sample(){
        for(var i=0; i<5; i++) {
            logger.debug("Run DEBUG");
            logger.info("Run INFO");
            logger.warn("Run WARN");
            logger.error("Run ERROR");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void sampleWithException(){
        for(var i=0; i<5; i++) {
            try {
                String.join(null, ",");
            } catch (Exception e) {
                logger.error("Generated exception", e);
            }
        }
    }
}
