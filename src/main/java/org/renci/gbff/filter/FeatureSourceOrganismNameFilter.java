package org.renci.gbff.filter;

import org.renci.gbff.Filter;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Sequence;

public class FeatureSourceOrganismNameFilter implements Filter {

    private String organism;

    public FeatureSourceOrganismNameFilter(String organism) {
        super();
        this.organism = organism;
    }

    @Override
    public boolean accept(Sequence sequence) {
        for (Feature feature : sequence.getFeatures()) {
            if ("source".equals(feature.getType()) && feature.getQualifiers().containsKey("organism")
                    && feature.getQualifiers().getProperty("organism").contains(organism)) {
                return true;
            }
        }
        return false;
    }

}
