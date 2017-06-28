package org.renci.gbff.model;

import java.io.Serializable;

public class Source implements Serializable {

    private static final long serialVersionUID = 3248671706404946050L;

    private String organism;

    private String description;

    public Source() {
        super();
    }

    public Source(String organism, String description) {
        this();
        this.description = description;
        this.organism = organism;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Source [organism=%s, description=%s]", organism, description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((organism == null) ? 0 : organism.hashCode());
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
        Source other = (Source) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (organism == null) {
            if (other.organism != null)
                return false;
        } else if (!organism.equals(other.organism))
            return false;
        return true;
    }

}
