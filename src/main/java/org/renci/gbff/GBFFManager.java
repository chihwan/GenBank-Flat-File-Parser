package org.renci.gbff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.renci.gbff.model.Sequence;

public class GBFFManager {

    private static GBFFManager instance;

    public static GBFFManager getInstance() {
        if (instance == null) {
            instance = new GBFFManager();
        }
        return instance;
    }

    private GBFFManager() {
        super();
    }

    public List<Sequence> deserialize(File gbFile) {
        return deserialize(null, gbFile);
    }

    public List<Sequence> deserialize(final GBFFFilter filter, File gbFile) {
        return deserialize(filter, false, gbFile);
    }

    public List<Sequence> deserialize(final GBFFFilter filter, boolean skipOrigin, File gbFile) {
        List<Sequence> ret = new ArrayList<Sequence>();
        try {
            GBFFDeserializer deserializer = new GBFFDeserializer(gbFile, filter, skipOrigin);
            ret.addAll(deserializer.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void serialize(File gbFile, List<Sequence> sequences) {
        GBFFSerializer serializer = new GBFFSerializer(gbFile, sequences);
        serializer.run();
    }

}
