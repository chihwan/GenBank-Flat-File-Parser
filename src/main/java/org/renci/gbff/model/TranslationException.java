package org.renci.gbff.model;

import org.apache.commons.lang3.Range;

public class TranslationException {

    private Range<Integer> range;

    private String aminoAcid;

    public TranslationException() {
        super();
    }

    public TranslationException(Range<Integer> range, String aminoAcid) {
        super();
        this.range = range;
        this.aminoAcid = aminoAcid;
    }

    public Range<Integer> getRange() {
        return range;
    }

    public void setRange(Range<Integer> range) {
        this.range = range;
    }

    public String getAminoAcid() {
        return aminoAcid;
    }

    public void setAminoAcid(String aminoAcid) {
        this.aminoAcid = aminoAcid;
    }

    @Override
    public String toString() {
        return String.format("TranslationException [range=%s, aminoAcid=%s]", range, aminoAcid);
    }

}
