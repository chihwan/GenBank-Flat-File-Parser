package org.renci.gbff.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Feature implements Serializable {

    private static final long serialVersionUID = -8909134055248889059L;

    private String type;

    private String location;

    private Map<String, String> qualifiers;

    private Map<String, String> dbXRefs;

    private List<TranslationException> translationExceptions;

    public Feature() {
        super();
        this.qualifiers = new HashMap<String, String>(20);
        this.dbXRefs = new HashMap<String, String>(20);
        this.translationExceptions = new ArrayList<>();
    }

    public Feature(String type, String location) {
        this();
        this.type = type;
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, String> getQualifiers() {
        return qualifiers;
    }

    public void setQualifiers(Map<String, String> qualifiers) {
        this.qualifiers = qualifiers;
    }

    public Map<String, String> getDbXRefs() {
        return dbXRefs;
    }

    public void setDbXRefs(Map<String, String> dbXRefs) {
        this.dbXRefs = dbXRefs;
    }

    public List<TranslationException> getTranslationExceptions() {
        return translationExceptions;
    }

    public void setTranslationExceptions(List<TranslationException> translationExceptions) {
        this.translationExceptions = translationExceptions;
    }

    @Override
    public String toString() {
        return String.format("Feature [type=%s, location=%s]", type, location);
    }

}
