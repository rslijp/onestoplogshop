package nl.softcause.onestoplogshop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MaintenanceService {

    private static final Logger logger = LoggerFactory.getLogger(MaintenanceService.class);

    private final long max_count;
    private final EntityManager entityManager;

    public MaintenanceService(@Value("${onestoplogshop.maintenance.max-rows}") long max_count, EntityManager entityManager) {
        this.entityManager = entityManager;
        this.max_count=max_count;
    }

    public long getTableRowCount(String tableName){
        String sql = String.format("select count(*) from %s",tableName);
        Query query = entityManager.createNativeQuery(sql);
        List<Long> empObject = query.getResultList();
        long value = empObject.get(0).longValue();
        logger.debug("Table size of {} is {}", tableName, value);
        return value;
    }


    public void deleteRowCount(String tableName, long count){
        String sql = String.format("DELETE FROM %s LIMIT %s", tableName, count);
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }


    @Async
    @Transactional
    @Scheduled(fixedDelay = 5000)
    public void purgeExcessRows() throws InterruptedException {
        var count=  getTableRowCount("logging_event");
        logger.debug("Checking row count max {}, actual {}", max_count, count);

        if(count>max_count){
            long to_purge = count-max_count;
            logger.info("Purging old rows {}", to_purge);
            deleteRowCount("logging_event", to_purge);
        }
    }
}
