package org.renci.gbff.filter;

import java.util.List;

import org.renci.gbff.GBFFFilter;
import org.renci.gbff.model.Sequence;

public class GBFFAndFilter implements GBFFFilter {

    private List<GBFFFilter> filters;

    public GBFFAndFilter(List<GBFFFilter> filters) {
        super();
        this.filters = filters;
    }

    @Override
    public boolean accept(Sequence sequence) {
        for (GBFFFilter f : filters) {
            if (!f.accept(sequence)) {
                return false;
            }
        }
        return true;
    }

}
