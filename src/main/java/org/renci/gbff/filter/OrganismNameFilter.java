package org.renci.gbff.filter;

import org.apache.commons.lang3.StringUtils;
import org.renci.gbff.Filter;
import org.renci.gbff.model.Sequence;

public class OrganismNameFilter implements Filter {

    private String organism;

    public OrganismNameFilter(String organism) {
        super();
        this.organism = organism;
    }

    @Override
    public boolean accept(Sequence sequence) {
        if (sequence.getSource() != null && StringUtils.isNoneEmpty(sequence.getSource().getOrganism())
                && sequence.getSource().getOrganism().contains(organism)) {
            return true;
        }
        return false;
    }

}
