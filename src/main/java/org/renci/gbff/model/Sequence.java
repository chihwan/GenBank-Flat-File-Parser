package org.renci.gbff.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sequence implements Serializable {

    private static final long serialVersionUID = -2883469898149057451L;

    private String locus;

    private String definition;

    private String accession;

    private String version;

    private String dbLink;

    private String keywords;

    private Source source;

    private Comment comment;

    private List<Reference> references;

    private List<Feature> features;

    private List<Origin> origin;

    public Sequence() {
        super();
        references = new ArrayList<Reference>();
        features = new ArrayList<Feature>();
        origin = new ArrayList<Origin>();
    }

    public Sequence(String locus, String definition, String accession, String version, String dbLink, String keywords) {
        this();
        this.locus = locus;
        this.definition = definition;
        this.accession = accession;
        this.version = version;
        this.dbLink = dbLink;
        this.keywords = keywords;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public String getLocus() {
        return locus;
    }

    public void setLocus(String locus) {
        this.locus = locus;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getAccession() {
        return accession;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDbLink() {
        return dbLink;
    }

    public void setDbLink(String dbLink) {
        this.dbLink = dbLink;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public List<Origin> getOrigin() {
        return origin;
    }

    public void setOrigin(List<Origin> origin) {
        this.origin = origin;
    }

    @Override
    public String toString() {
        return String.format("Sequence [accession=%s, version=%s, dbLink=%s, keywords=%s]", accession, version, dbLink,
                keywords);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accession == null) ? 0 : accession.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((dbLink == null) ? 0 : dbLink.hashCode());
        result = prime * result + ((definition == null) ? 0 : definition.hashCode());
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        result = prime * result + ((locus == null) ? 0 : locus.hashCode());
        result = prime * result + ((source == null) ? 0 : source.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
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
        Sequence other = (Sequence) obj;
        if (accession == null) {
            if (other.accession != null)
                return false;
        } else if (!accession.equals(other.accession))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (dbLink == null) {
            if (other.dbLink != null)
                return false;
        } else if (!dbLink.equals(other.dbLink))
            return false;
        if (definition == null) {
            if (other.definition != null)
                return false;
        } else if (!definition.equals(other.definition))
            return false;
        if (keywords == null) {
            if (other.keywords != null)
                return false;
        } else if (!keywords.equals(other.keywords))
            return false;
        if (locus == null) {
            if (other.locus != null)
                return false;
        } else if (!locus.equals(other.locus))
            return false;
        if (source == null) {
            if (other.source != null)
                return false;
        } else if (!source.equals(other.source))
            return false;
        if (version == null) {
            if (other.version != null)
                return false;
        } else if (!version.equals(other.version))
            return false;
        return true;
    }

}
