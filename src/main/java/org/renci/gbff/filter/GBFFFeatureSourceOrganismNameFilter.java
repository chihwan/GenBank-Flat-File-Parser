package org.renci.gbff.filter;

import org.renci.gbff.GBFFFilter;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Sequence;

public class GBFFFeatureSourceOrganismNameFilter implements GBFFFilter {

    private String organism;

    public GBFFFeatureSourceOrganismNameFilter(String organism) {
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
