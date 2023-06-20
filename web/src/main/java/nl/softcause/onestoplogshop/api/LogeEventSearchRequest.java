package nl.softcause.onestoplogshop.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nl.softcause.onestoplogshop.model.LogginEvent;
import nl.softcause.onestoplogshop.search.SearchRequestBase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

@JsonIgnoreProperties({"specification", "specificationWithoutExclusion"})
public class LogeEventSearchRequest extends SearchRequestBase<LogginEvent> {

    private Long fromId;
    private String message;

    /**
     * @noinspection WeakerAccess
     */
    public LogeEventSearchRequest() {
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


    @Override
    public Specification<LogginEvent> getSpecification() {
        Specification<LogginEvent> spec = null;
        if(fromId !=null){
            spec = add(spec, Specification.where(messageFromId(fromId)));
        }
        if (StringUtils.isNotBlank(message)) {
            String keyword = getMessage().toLowerCase();
            spec = add(spec, Specification.where(messageContainsIgnoreCase(keyword)));

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
}
