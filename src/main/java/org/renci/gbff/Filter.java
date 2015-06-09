package org.renci.gbff;

import org.renci.gbff.model.Sequence;

public interface Filter {

    public boolean accept(Sequence sequence);

}
