package org.renci.gbff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPInputStream;

import org.renci.gbff.model.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GBFFManager {

    private final Logger logger = LoggerFactory.getLogger(GBFFManager.class);

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

    public List<Sequence> deserialize(File... gbFiles) {
        return deserialize(null, gbFiles);
    }

    public List<Sequence> deserialize(Filter filter, File... gbFiles) {
        List<Sequence> ret = new ArrayList<Sequence>();
        for (File f : gbFiles) {
            logger.info("deserializing: {}", f.getName());

            long start = System.currentTimeMillis();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(f))))) {
                String line;

                LinkedList<String> lines = new LinkedList<String>();
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("//")) {
                        try {
                            Sequence info = new GBFFDeserializer(lines, filter).call();
                            if (info != null) {
                                ret.add(info);
                            }
                            lines.clear();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    lines.add(String.format("%s%n", line));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            logger.debug("duration: {} seconds", (end - start) / 1000);
        }

        return ret;
    }

    public void serialize(File gbFile, List<Sequence> sequences) {
        GBFFSerializer serializer = new GBFFSerializer(gbFile, sequences);
        serializer.run();
    }

}
