package nl.softcause.onestoplogshop.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogginEventRepository
            extends JpaRepository<LoggingEvent, Long>, JpaSpecificationExecutor<LoggingEvent> {

    List<LoggingEvent> findFirst20WithIdBeforeOrderDescById(Long id);

    @Query(value = "select distinct p from LoggingEvent e join e.properties as p where index(p)=:field", nativeQuery = false)
    List<String> findFieldValues(@Param("field") String field);
}