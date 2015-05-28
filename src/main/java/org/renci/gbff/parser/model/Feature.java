package org.renci.gbff.parser.model;

import java.io.Serializable;
import java.util.Properties;

public class Feature implements Serializable {

    private static final long serialVersionUID = -8909134055248889059L;

    private String type;

    private String location;

    private Properties qualifiers;

    public Feature() {
        super();
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

    public Properties getQualifiers() {
        if (qualifiers == null) {
            qualifiers = new Properties();
        }
        return qualifiers;
    }

    public void setQualifiers(Properties qualifiers) {
        this.qualifiers = qualifiers;
    }

    @Override
    public String toString() {
        return String.format("Feature [type=%s, location=%s]", type, location);
    }

}
