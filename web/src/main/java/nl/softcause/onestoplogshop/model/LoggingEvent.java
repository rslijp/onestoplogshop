package nl.softcause.onestoplogshop.model;

import ch.qos.logback.classic.spi.LoggingEventVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.softcause.onestoplogshop.util.InstantSerializer;
import org.hibernate.annotations.BatchSize;

@BatchSize(size = 20)
@Table(name = "LOGGING_EVENT")
@Entity
public class LoggingEvent {

    @Id
    @GeneratedValue
    @Column(name = "EVENT_ID")
    private Long id;
    @Column(name = "TIMESTMP")
    private long epochTimeStamp;
    @Column(name = "LEVEL_STRING")
    private String level;
    @Column(name = "FORMATTED_MESSAGE")
    private String message;

    @Column(name = "LOGGER_NAME")
    private String loggerName;


    @Column(name = "CALLER_FILENAME")
    private String fileName;

    @Column(name = "CALLER_CLASS")
    private String className;

    @Column(name = "CALLER_METHOD")
    private String methodName;

    @Column(name = "CALLER_LINE")
    private Long lineNumber;

    @Column(name = "THREAD_NAME")
    private String threadName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event", targetEntity = LoggingEventStackTrace.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value = "i")
    private List<LoggingEventStackTrace> stackTraces;

    @ElementCollection
    @JoinTable(name = "LOGGING_EVENT_PROPERTY", joinColumns = @JoinColumn(name = "EVENT_ID"))
    @MapKeyColumn(name = "MAPPED_KEY")
    @Column(name = "MAPPED_VALUE")
    private Map<String, String> properties;

    public static LoggingEvent fromText(String message, String loggerName, Map<String, String> properties) {
        var msg = new LoggingEvent();
        msg.level = "INFO";
        msg.loggerName = loggerName;
        msg.message = message;
        msg.epochTimeStamp = System.currentTimeMillis();
        msg.properties = properties;
        return msg;
    }

    public static LoggingEvent fromVo(LoggingEventVO vo) {
        var msg = new LoggingEvent();
        msg.level = "INFO";
        msg.epochTimeStamp = vo.getTimeStamp();
        msg.level = vo.getLevel().toString();
        msg.message = vo.getFormattedMessage();
        msg.loggerName = vo.getLoggerContextVO().getName();
        if (vo.hasCallerData() && vo.getCallerData().length > 0) {
            msg.fileName = vo.getCallerData()[0].getFileName();
            msg.className = vo.getCallerData()[0].getClassName();
            msg.methodName = vo.getCallerData()[0].getMethodName();
            msg.lineNumber = Long.valueOf(vo.getCallerData()[0].getLineNumber());
        }
        msg.threadName = vo.getThreadName();
        msg.epochTimeStamp = System.currentTimeMillis();
        msg.properties = vo.getMDCPropertyMap();
        if (vo.hasCallerData() && vo.getCallerData().length > 1) {
            msg.stackTraces = new ArrayList<>();
            for (var i = 0; i < vo.getCallerData().length; i++) {
                msg.stackTraces.add(LoggingEventStackTrace.fromVo(msg, i, vo.getCallerData()[i]));
            }
        }
//        msg.stackTraces= Arrays.stream(vo.getCallerData()).map((ste,i) -> LoggingEventStackTrace.fromVo(ste, i)).collect(Collectors.toList());
        return msg;
    }

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event", targetEntity = LogginEventProperty.class, cascade = CascadeType.ALL, orphanRemoval = true)
//    @OrderBy(value="key")
//    private List<LogginEventProperty> properties;


    public Long getId() {
        return id;
    }

    @JsonIgnore
    public long getEpochTimeStamp() {
        return epochTimeStamp;
    }

    @JsonSerialize(using = InstantSerializer.class)
    public Instant getAt() {
        return Instant.ofEpochMilli(epochTimeStamp);
    }

    public String getLevel() {
        return level;
    }

    public String getMessage() {
        return message;
    }


    public String getFileName() {
        return fileName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public Long getLineNumber() {
        return lineNumber;
    }

    public String getThreadName() {
        return threadName;
    }

    @JsonIgnore
    public List<LoggingEventStackTrace> getStackTraces() {
        return stackTraces;
    }

//    @JsonIgnore
//    public List<LogginEventProperty> getProperties() {
//        return properties;
//    }

    public String[] getStackTrace() {
        if (getStackTraces() == null || getStackTraces().size() == 0) {
            return null;
        }
        return getStackTraces().stream().map(s -> s.getLine()).toArray(String[]::new);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

//    public Map<String, String> getPropertyMap() {
//        var map = new TreeMap<String, String>();
//        for (var pair : getProperties()) {
//            map.put(pair.getKey(), pair.getValue());
//        }
//        return map;
//    }

}
