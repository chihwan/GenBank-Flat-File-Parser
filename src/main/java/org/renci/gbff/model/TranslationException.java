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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((aminoAcid == null) ? 0 : aminoAcid.hashCode());
        result = prime * result + ((range == null) ? 0 : range.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TranslationException other = (TranslationException) obj;
        if (aminoAcid == null) {
            if (other.aminoAcid != null)
                return false;
        } else if (!aminoAcid.equals(other.aminoAcid))
            return false;
        if (range == null) {
            if (other.range != null)
                return false;
        } else if (!range.equals(other.range))
            return false;
        return true;
    }

}
