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

    public Comment(String description) {
        super();
        this.description = description;
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
        return String.format("Comment [description=%s]", description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
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
        Comment other = (Comment) obj;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }

}
