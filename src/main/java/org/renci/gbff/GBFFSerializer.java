package org.renci.gbff;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Origin;
import org.renci.gbff.model.Sequence;
import org.renci.gbff.model.Source;

public class GBFFSerializer implements Runnable, Constants {

    private File gbFile;

    private List<Sequence> sequences;

    public GBFFSerializer(File gbFile, List<Sequence> sequences) {
        super();
        this.gbFile = gbFile;
        this.sequences = sequences;
    }

    @Override
    public void run() {

        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(gbFile))))) {
            for (Sequence sequence : sequences) {
                bw.write(String.format("%-12s%s%n", LOCUS_TAG, sequence.getLocus()));
                bw.write(String.format("%-12s%s%n", DEFINITION_TAG, sequence.getDefinition()));
                bw.write(String.format("%-12s%s%n", ACCESSION_TAG, sequence.getAccession()));
                bw.write(String.format("%-12s%s%n", VERSION_TAG, sequence.getVersion()));
                bw.write(String.format("%-12s%s%n", KEYWORDS_TAG, sequence.getKeywords()));
                Source source = sequence.getSource();
                bw.write(String.format("%-12s%s%n", SOURCE_TAG, source.getDescription()));
                bw.write(String.format("  %-10s%s%n", ORGANISM_TAG,
                        source.getOrganism().replace("\n", "\n            ")));
                bw.write(String.format("%-12s%s%n", COMMENT_TAG,
                        sequence.getComment().getDescription().replace("\n", "\n" + StringUtils.repeat(" ", 12))));
                bw.write(String.format("%-21s%s%n", FEATURES_TAG, "Location/Qualifiers"));
                for (Feature feature : sequence.getFeatures()) {
                    bw.write(String.format("     %-16s%s%n", feature.getType(), feature.getLocation()));
                    Map<String, String> properties = feature.getQualifiers();
                    for (Object key : properties.keySet()) {
                        String value = properties.get(key.toString()).replace("\n", "\n" + StringUtils.repeat(" ", 21));
                        if ("codon_start".equals(key)) {
                            bw.write(String.format("%s/%s=%s%n", StringUtils.repeat(" ", 21), key, value));
                        } else {
                            bw.write(String.format("%s/%s=\"%s\"%n", StringUtils.repeat(" ", 21), key, value));
                        }
                    }
                }
                bw.write(String.format("%s%n", ORIGIN_TAG));
                for (Origin origin : sequence.getOrigin()) {

                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < origin.getSequence().length(); i++) {
                        if (i % 10 == 0) {
                            sb.append(" ");
                        }
                        sb.append(origin.getSequence().charAt(i));
                    }

                    bw.write(String.format("%s%s%n", StringUtils.leftPad(origin.getIndex().toString(), 9),
                            sb.toString()));
                }

                bw.write(String.format("%s%n", END_SEQUENCE_TAG));
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
