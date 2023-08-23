package nl.softcause.onestoplogshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "onestoplogshop")
@PropertySource(value = "classpath:logging.yml", factory = YamlPropertySourceFactory.class)
public class LoggingProperties {

    private ColumnDTO[] additionalColumns;

    private FilterDTO[] additionalFilter;

    public LoggingProperties(){

    }

//    public LoggingProperties(ColumnDTO[] additionalColumns, String[] additionalFilter) {
//        this.additionalColumns = additionalColumns;
//        this.additionalFilter = additionalFilter;
//    }

    public ColumnDTO[] getAdditionalColumns() {
        return additionalColumns;
    }

    public void setAdditionalColumns(ColumnDTO[] additionalColumns) {
        this.additionalColumns = additionalColumns;
    }

    public FilterDTO[] getAdditionalFilter() {
        return additionalFilter;
    }

    public void setAdditionalFilter(FilterDTO[] additionalFilter) {
        this.additionalFilter = additionalFilter;
    }

    public static class ColumnDTO {
        private String name;
        private String field;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }

    public static class FilterDTO {
        private String name;
        private String field;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}
