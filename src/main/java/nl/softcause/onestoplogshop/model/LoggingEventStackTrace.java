package nl.softcause.onestoplogshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import nl.softcause.onestoplogshop.util.InstantSerializer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Table(name = "LOGGING_EVENT_EXCEPTION")
@Entity
@IdClass(LoggingEventStackTraceId.class)
public class LoggingEventStackTrace {

    @Id
    @Column(name = "I")
    private Long i;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EVENT_ID", nullable = true)
    @Fetch(value = FetchMode.SELECT)
    private LogginEvent event;

    @Column(name="TRACE_LINE")
    private String line;

    public Long getI() {
        return i;
    }

    public LogginEvent getEvent() {
        return event;
    }

    public String getLine() {
        return line;
    }
}
