package nl.softcause.onestoplogshop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private LoggingEvent event;

    @Column(name="TRACE_LINE")
    private String line;



    public static LoggingEventStackTrace fromVo(LoggingEvent event, int i, StackTraceElement ste) {
        var r = new LoggingEventStackTrace();
        r.i=Long.valueOf(i);
        r.line=ste.toString();
        r.event=event;
        return r;
    }

    public Long getI() {
        return i;
    }

    public LoggingEvent getEvent() {
        return event;
    }

    public String getLine() {
        return line;
    }
}
