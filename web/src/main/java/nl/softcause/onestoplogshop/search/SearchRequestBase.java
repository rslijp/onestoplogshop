package nl.softcause.onestoplogshop.search;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.Specification;

public abstract class SearchRequestBase<T> extends SearchRequest {

    protected Specification<T> add(Specification<T> lhs, Specification<T> rhs) {
        if (lhs == null) {
            return Specification.where(rhs);
        } else {
            return lhs.and(rhs);
        }
    }

    protected Specification<T> contains(final String field, final String keyword) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), "%" + keyword.toLowerCase() + "%");
    }

    protected Specification<T> startsWith(final String field, final String keyword) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(field)), keyword.toLowerCase() + "%");
    }

    protected Specification<T> isEqual(String field, String value) {
        return (root, query, cb) -> cb.equal(root.get(field), value);
    }


    protected Specification<T> dateRange(String field, final DateRange at) {
        Specification<T> spec = null;
        if (at.getMin() != null) {
            spec = add(spec, dateRangeFrom(field, at));
        }
        if (at.getMax() != null) {
            spec = add(spec, dateRangeUntil(field, at));
        }
        return spec;
    }


    protected Specification<T> dateRangeFrom(final String field, final DateRange at) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(field), at.getMin());
    }

    protected Specification<T> dateRangeUntil(final String field, final DateRange at) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get(field), at.getMax());
    }

    @JsonIgnore
    public abstract Specification<T> getSpecification();
}
