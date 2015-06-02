package org.renci.gbff.parser.model;

import java.io.Serializable;
import java.util.Properties;

public class Comment implements Serializable {

    private static final long serialVersionUID = 8680319037045355556L;

    private String description;

    private Properties genomeAnnotations;

    public Comment() {
        super();
        this.genomeAnnotations = new Properties();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Properties getGenomeAnnotations() {
        return genomeAnnotations;
    }

    public void setGenomeAnnotations(Properties genomeAnnotations) {
        this.genomeAnnotations = genomeAnnotations;
    }

    @Override
    public String toString() {
        return String.format("Comment [description=%s, genomeAnnotations=%s]", description, genomeAnnotations);
    }

}
