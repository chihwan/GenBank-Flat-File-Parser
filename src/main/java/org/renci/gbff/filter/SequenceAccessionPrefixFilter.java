package org.renci.gbff.filter;

import java.util.List;

import org.renci.gbff.Filter;
import org.renci.gbff.model.Sequence;

public class SequenceAccessionPrefixFilter implements Filter {

    private List<String> prefixes;

    public SequenceAccessionPrefixFilter(List<String> prefixes) {
        super();
        this.prefixes = prefixes;
    }

    @Override
    public boolean accept(Sequence sequence) {
        for (String prefix : prefixes) {
            if (sequence.getAccession().startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

}
