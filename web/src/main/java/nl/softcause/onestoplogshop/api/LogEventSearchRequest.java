package nl.softcause.onestoplogshop.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import nl.softcause.onestoplogshop.model.LogginEvent;
import nl.softcause.onestoplogshop.search.SearchRequestBase;
import nl.softcause.onestoplogshop.util.InstantDeserializer;
import nl.softcause.onestoplogshop.util.InstantSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@JsonIgnoreProperties({"specification", "specificationWithoutExclusion"})
public class LogEventSearchRequest extends SearchRequestBase<LogginEvent> {

    private Long fromId;
    private String message;
    private String level;
    private Map<String, String> properties = new HashMap<>();

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant startDate;

    @JsonSerialize(using = InstantSerializer.class)
    @JsonDeserialize(using = InstantDeserializer.class)
    private Instant endDate;

    /**
     * @noinspection WeakerAccess
     */
    public LogEventSearchRequest() {
        super();
        setSortOn("id");
        setSortDir("DESC");
    }

    private static Specification<LogginEvent> messageContainsIgnoreCase(String keyword) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("message")), "%" + keyword + "%");
    }

    private static Specification<LogginEvent> messageFromId(Long id) {
        return (root, query, cb) -> cb.lessThan(root.get("id"), id);
    }

    private static Specification<LogginEvent> isLevel(String level) {
        return (root, query, cb) -> cb.equal(root.get("level"), level);
    }

    @Override
    public Specification<LogginEvent> getSpecification() {
        Specification<LogginEvent> spec = null;
        if (fromId != null) {
            spec = add(spec, Specification.where(messageFromId(fromId)));
        }
        if (StringUtils.isNotBlank(message)) {
            String keyword = getMessage().toLowerCase();
            spec = add(spec, Specification.where(messageContainsIgnoreCase(keyword)));

        }
        if (StringUtils.isNotBlank(level)) {
            spec = add(spec, Specification.where(isLevel(level)));

        }

        if (startDate != null) {
            spec = add(spec, dateRangeFrom("epochTimeStamp", startDate));
        }
        if (endDate != null) {
            spec = add(spec, dateRangeUntil("epochTimeStamp", endDate));
        }
        for (var pair : properties.entrySet()) {
            if (pair.getValue() != null) {
                spec = add(spec, hasMapProperty("properties", pair.getKey(), pair.getValue()));
            }
        }
        return spec;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }
}
