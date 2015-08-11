package org.renci.gbff.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Feature implements Serializable {

    private static final long serialVersionUID = -8909134055248889059L;

    private String type;

    private String location;

    private Map<String, String> qualifiers;

    private Map<String, String> DBXrefs;

    public Feature() {
        super();
        this.qualifiers = new HashMap<String, String>(20);
        this.DBXrefs = new HashMap<String, String>(20);
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

    public Map<String, String> getDBXrefs() {
        return DBXrefs;
    }

    public void setDBXrefs(Map<String, String> dBXrefs) {
        DBXrefs = dBXrefs;
    }

    @Override
    public String toString() {
        return String.format("Feature [type=%s, location=%s]", type, location);
    }

}
