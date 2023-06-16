package nl.softcause.onestoplogshop.search;

import java.util.Collections;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class SearchRequest {

    private int pageSize = 20;

    private String sortOn="id";

    private String sortDir = "DESC";

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortOn() {
        return sortOn;
    }

    public void setSortOn(String sortOn) {
        this.sortOn = sortOn;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir;
    }

    protected Map<String, String> getColumnRewrites() {
        return Collections.emptyMap();
    }

    private String rewriteColumn(String column) {
        if (getColumnRewrites().containsKey(column)) {
            return getColumnRewrites().get(column);
        }
        return column;
    }

    public Pageable asPageable() {
        return PageRequest.of(0, getPageSize(), asSort());
    }


    public Sort asSort() {
        Sort sort = null;
        if (sortOn != null) {
            sort = Sort.by("ASC".equals(sortDir) ? Direction.ASC : Direction.DESC, sortOn);
        }
        return sort;
    }

}
