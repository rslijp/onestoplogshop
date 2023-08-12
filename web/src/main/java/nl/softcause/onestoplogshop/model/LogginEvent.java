package nl.softcause.onestoplogshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import nl.softcause.onestoplogshop.util.InstantSerializer;
import org.hibernate.annotations.BatchSize;

@BatchSize(size = 20)
@Table(name = "LOGGING_EVENT")
@Entity
public class LogginEvent {

    @Id
    @Column(name = "EVENT_ID")
    private Long id;
    @Column(name="TIMESTMP")
    private long epochTimeStamp;
    @Column(name="LEVEL_STRING")
    private String level;
    @Column(name="FORMATTED_MESSAGE")
    private String message;

    @Column(name="CALLER_FILENAME")
    private String fileName;

    @Column(name="CALLER_CLASS")
    private String className;

    @Column(name="CALLER_METHOD")
    private String methodName;

    @Column(name="CALLER_LINE")
    private Long lineNumber;

    @Column(name="THREAD_NAME")
    private String threadName;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "event", targetEntity = LoggingEventStackTrace.class, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy(value="i")
    private List<LoggingEventStackTrace> stackTraces;

    @ElementCollection
    @JoinTable(name="LOGGING_EVENT_PROPERTY", joinColumns=@JoinColumn(name="EVENT_ID"))
    @MapKeyColumn(name="MAPPED_KEY")
    @Column(name="MAPPED_VALUE")
    private Map<String, String> properties;

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
        if(getStackTraces()==null || getStackTraces().size()==0) return null;
        return getStackTraces().stream().map(s->s.getLine()).toArray(String[]::new);
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
