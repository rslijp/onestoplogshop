package nl.softcause.onestoplogshop.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import nl.softcause.onestoplogshop.api.DiscoverLogController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class YamlPropertySourceFactory implements PropertySourceFactory {
    private static final Logger logger = LoggerFactory.getLogger(YamlPropertySourceFactory.class);

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        try {
            final YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            var properties = factory.getObject();
            return new PropertiesPropertySource(resource.getResource().getFilename(), properties);
        } catch (final java.lang.IllegalStateException illegalState) {
            if (illegalState.getCause() instanceof final FileNotFoundException e) {
                logger.info("Optional property source {} not found. Using embedded resource.", resource.getResource().getFilename());
                // ConfigurationClassParser wants FileNotFoundException to honor PropertySource.ignoreResourceNotFound
                throw e;
            } else {
                throw illegalState;
            }
        }
//
//        var factory = new YamlPropertiesFactoryBean();
//        factory.setResources(resource.getResource());
//
//        var properties = factory.getObject();
//
//        return new PropertiesPropertySource(resource.getResource().getFilename(), properties);
    }
}
