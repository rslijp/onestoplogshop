package nl.softcause.onestoplogshop.api;

import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import nl.softcause.onestoplogshop.config.LoggingProperties;
import nl.softcause.onestoplogshop.model.LogginEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatisticsController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    private final EntityManager entityManager;

    public StatisticsController(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    public long getTableRowCount(String tableName){
        String sql = String.format("select count(*) from %s",tableName);
        Query query = entityManager.createNativeQuery(sql);
        List<BigInteger> empObject = query.getResultList();
        long value = empObject.get(0).longValue();
        logger.debug("Table size of {} is {}", tableName, value);
        return value;
    }

    public long getTableSizeInBytes(String tableName){
        String sql = String.format("CALL DISK_SPACE_USED('%s');",tableName);
        Query query = entityManager.createNativeQuery(sql);
        List<BigInteger> empObject = query.getResultList();
        long value = empObject.get(0).longValue();
        logger.debug("Table size of {} is {}", tableName, value);
        return value;
    }

    public long getTotalSizeInBytes(){
        String sql = "SELECT SETTING_VALUE FROM INFORMATION_SCHEMA.SETTINGS WHERE SETTING_NAME = 'info.FILE_SIZE';";
        Query query = entityManager.createNativeQuery(sql);
        List<String> empObject = query.getResultList();
        long value = Long.valueOf(empObject.get(0));
        logger.debug("Total database size is {}", value);
        return value;
    }

    @GetMapping(path = "/api/statistics")
    public @ResponseBody
    DatabaseStatistics retrieve() {
        logger.debug("Api database statistics call");
        return new DatabaseStatistics(
                getTotalSizeInBytes(),
                getTableSizeInBytes("logging_event"),
                getTableSizeInBytes("logging_event_property"),
                getTableSizeInBytes("logging_event_exception"),
                getTableRowCount("logging_event"),
                getTableRowCount("logging_event_property"),
                getTableRowCount("logging_event_exception")
        );
    }


    public static class DatabaseStatistics {
        public Long databaseFileSize;
        public Long loggingEventFileSize;
        public Long loggingEventPropertyFileSize;
        public Long loggingEventExceptionFileSize;
        public Long loggingEventRowCount;
        public Long loggingEventPropertyRowCount;
        public Long loggingEventExceptionRowCount;

        public DatabaseStatistics(Long databaseFileSize, Long loggingEventFileSize, Long loggingEventPropertyFileSize,
                                  Long loggingEventExceptionFileSize, Long loggingEventRowCount,
                                  Long loggingEventPropertyRowCount, Long loggingEventExceptionRowCount) {
            this.databaseFileSize = databaseFileSize;
            this.loggingEventFileSize = loggingEventFileSize;
            this.loggingEventPropertyFileSize = loggingEventPropertyFileSize;
            this.loggingEventExceptionFileSize = loggingEventExceptionFileSize;
            this.loggingEventRowCount = loggingEventRowCount;
            this.loggingEventPropertyRowCount = loggingEventPropertyRowCount;
            this.loggingEventExceptionRowCount = loggingEventExceptionRowCount;
        }

        public Long getDatabaseFileSize() {
            return databaseFileSize;
        }

        public Long getLoggingEventFileSize() {
            return loggingEventFileSize;
        }

        public Long getLoggingEventPropertyFileSize() {
            return loggingEventPropertyFileSize;
        }

        public Long getLoggingEventExceptionFileSize() {
            return loggingEventExceptionFileSize;
        }

        public Long getLoggingEventRowCount() {
            return loggingEventRowCount;
        }

        public Long getLoggingEventPropertyRowCount() {
            return loggingEventPropertyRowCount;
        }

        public Long getLoggingEventExceptionRowCount() {
            return loggingEventExceptionRowCount;
        }
    }
}
