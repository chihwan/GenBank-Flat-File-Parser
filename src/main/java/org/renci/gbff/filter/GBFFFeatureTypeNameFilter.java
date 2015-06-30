package org.renci.gbff.filter;

import java.util.HashSet;
import java.util.Set;

import org.renci.gbff.GBFFFilter;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Sequence;

public class GBFFFeatureTypeNameFilter implements GBFFFilter {

    private String type;

    public GBFFFeatureTypeNameFilter(String type) {
        super();
        this.type = type;
    }

    @Override
    public boolean accept(Sequence sequence) {
        Set<String> typeNameSet = new HashSet<String>();
        for (Feature feature : sequence.getFeatures()) {
            typeNameSet.add(feature.getType());
        }
        return typeNameSet.contains(type);
    }

}
