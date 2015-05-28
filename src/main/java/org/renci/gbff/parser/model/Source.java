package org.renci.gbff.parser.model;

import java.io.Serializable;

public class Source implements Serializable {

    private static final long serialVersionUID = 3248671706404946050L;

    private String description;

    private String organism;

    public Source() {
        super();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganism() {
        return organism;
    }

    public void setOrganism(String organism) {
        this.organism = organism;
    }

    @Override
    public String toString() {
        return String.format("Source [description=%s, organism=%s]", description, organism);
    }

}
