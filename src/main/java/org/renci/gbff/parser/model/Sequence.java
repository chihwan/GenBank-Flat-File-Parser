package org.renci.gbff.parser.model;

import java.util.ArrayList;
import java.util.List;

public class Sequence {

    private String locus;

    private String definition;

    private String accession;

    private String version;

    private String dbLink;

    private String keywords;

    private Source source;

    private List<Reference> references;

    private Comment comment;

    private List<Feature> features;

    private List<Origin> origin;

    public Sequence() {
        super();
        references = new ArrayList<Reference>();
        features = new ArrayList<Feature>();
        origin = new ArrayList<Origin>();
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

}
