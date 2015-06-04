package org.renci.gbff.parser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.zip.GZIPInputStream;

import org.renci.gbff.parser.model.Sequence;

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

    public List<Sequence> deserialize(File... gbFiles) {
        List<Sequence> ret = new ArrayList<Sequence>();

        for (File f : gbFiles) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(
                    new FileInputStream(f))))) {
                String line;

                LinkedList<String> lines = new LinkedList<String>();
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("//")) {
                        try {
                            Sequence info = new GBFFDeserializer(lines).call();
                            ret.add(info);
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
        }

        return ret;
    }

    public void serialize(File gbFile, List<Sequence> sequences) {
        
        GBFFSerializer serializer = new GBFFSerializer(gbFile, sequences);
        serializer.run();
        
    }

}
