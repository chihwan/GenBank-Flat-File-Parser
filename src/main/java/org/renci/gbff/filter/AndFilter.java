package org.renci.gbff.filter;

import java.util.List;

import org.renci.gbff.Filter;
import org.renci.gbff.model.Sequence;

public class AndFilter implements Filter {

    private List<Filter> filters;

    public AndFilter(List<Filter> filters) {
        super();
        this.filters = filters;
    }

    @Override
    public boolean accept(Sequence sequence) {
        for (Filter f : filters) {
            if (!f.accept(sequence)) {
                return false;
            }
        }
        return true;
    }

}
