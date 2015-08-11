package org.renci.gbff.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Comment implements Serializable {

    private static final long serialVersionUID = 8680319037045355556L;

    private String description;

    private Map<String, String> genomeAnnotations;

    public Comment() {
        super();
        this.genomeAnnotations = new HashMap<String, String>(20);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getGenomeAnnotations() {
        return genomeAnnotations;
    }

    public void setGenomeAnnotations(Map<String, String> genomeAnnotations) {
        this.genomeAnnotations = genomeAnnotations;
    }

    @Override
    public String toString() {
        return String.format("Comment [description=%s, genomeAnnotations=%s]", description, genomeAnnotations);
    }

}
