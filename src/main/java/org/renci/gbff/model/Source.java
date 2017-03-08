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

}
