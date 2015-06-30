package org.renci.gbff.filter;

import org.apache.commons.lang3.StringUtils;
import org.renci.gbff.GBFFFilter;
import org.renci.gbff.model.Sequence;

public class GBFFSourceOrganismNameFilter implements GBFFFilter {

    private String organism;

    public GBFFSourceOrganismNameFilter(String organism) {
        super();
        this.organism = organism;
    }

    @Override
    public boolean accept(Sequence sequence) {
        if (sequence.getSource() != null && StringUtils.isNotEmpty(sequence.getSource().getOrganism())
                && sequence.getSource().getOrganism().contains(organism)) {
            return true;
        }
        return false;
    }

}
