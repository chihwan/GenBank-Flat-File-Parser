package org.renci.gbff;

import org.renci.gbff.model.Sequence;

public interface GBFFFilter {

    public boolean accept(Sequence sequence);

}
