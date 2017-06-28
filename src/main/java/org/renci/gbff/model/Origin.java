package org.renci.gbff.model;

import java.io.Serializable;

public class Origin implements Serializable {

    private static final long serialVersionUID = 318127662439364742L;

    private Integer index;

    private String sequence;

    public Origin() {
        super();
    }

    public Origin(Integer index, String sequence) {
        super();
        this.index = index;
        this.sequence = sequence;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return String.format("Origin [index=%s, sequence=%s]", index, sequence);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((index == null) ? 0 : index.hashCode());
        result = prime * result + ((sequence == null) ? 0 : sequence.hashCode());
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
        Origin other = (Origin) obj;
        if (index == null) {
            if (other.index != null)
                return false;
        } else if (!index.equals(other.index))
            return false;
        if (sequence == null) {
            if (other.sequence != null)
                return false;
        } else if (!sequence.equals(other.sequence))
            return false;
        return true;
    }

}
