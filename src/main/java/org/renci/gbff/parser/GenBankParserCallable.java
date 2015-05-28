package org.renci.gbff.parser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.renci.gbff.parser.model.Feature;
import org.renci.gbff.parser.model.GenBankInfo;
import org.renci.gbff.parser.model.Source;

public class GenBankParserCallable implements Callable<GenBankInfo> {

    public static final String LOCUS_TAG = "LOCUS";

    public static final String DEFINITION_TAG = "DEFINITION";

    public static final String ACCESSION_TAG = "ACCESSION";

    public static final String VERSION_TAG = "VERSION";

    public static final String KEYWORDS_TAG = "KEYWORDS";

    public static final String SOURCE_TAG = "SOURCE";

    public static final String ORGANISM_TAG = "  ORGANISM";

    public static final String REFERENCE_TAG = "REFERENCE";

    public static final String AUTHORS_TAG = "AUTHORS";

    public static final String CONSORTIUM_TAG = "CONSRTM";

    public static final String TITLE_TAG = "TITLE";

    public static final String JOURNAL_TAG = "JOURNAL";

    public static final String PUBMED_TAG = "PUBMED";

    public static final String MEDLINE_TAG = "MEDLINE";

    public static final String REMARK_TAG = "REMARK";

    public static final String COMMENT_TAG = "COMMENT";

    public static final String FEATURES_TAG = "FEATURES";

    public static final String BASE_COUNT_TAG_FULL = "BASE COUNT";

    public static final String BASE_COUNT_TAG = "BASE";

    public static final String ORIGIN_TAG = "ORIGIN";

    public static final String END_SEQUENCE_TAG = "//";

    private LinkedList<String> lines;

    public GenBankParserCallable(LinkedList<String> lines) {
        super();
        this.lines = lines;
    }

    @Override
    public GenBankInfo call() throws Exception {
        GenBankInfo info = new GenBankInfo();

        Iterator<String> lineIter = lines.iterator();
        while (lineIter.hasNext()) {
            String line = lineIter.next().trim();
            if (line.startsWith(LOCUS_TAG)) {
                info.setLocus(line.substring(12, line.length()));
            }

            if (line.startsWith(ACCESSION_TAG)) {
                info.setAccession(line.substring(12, line.length()));
            }

            if (line.startsWith(VERSION_TAG)) {
                info.setVersion(line.substring(12, line.length()));
            }

            if (line.startsWith(KEYWORDS_TAG)) {
                info.setKeywords(line.substring(12, line.length()));
            }

            if (line.startsWith(SOURCE_TAG)) {
                Source source = new Source();
                source.setDescription(line.substring(12, line.length()));
                info.setSource(source);
            }

            if (line.startsWith(ORGANISM_TAG)) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s%n", line.substring(12, line.length())));
                do {
                    line = lineIter.next();
                    sb.append(String.format("%s%n", line));
                } while (!line.trim().endsWith("."));
                info.getSource().setOrganism(sb.toString());
            }

            if (line.startsWith(DEFINITION_TAG)) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s%n", line.substring(12, line.length())));
                do {
                    line = lineIter.next();
                    sb.append(String.format("%s%n", line));
                } while (!line.trim().endsWith("."));
                info.setDefinition(sb.toString());
            }

            if (line.startsWith(FEATURES_TAG)) {
                line = lineIter.next();
                while (!line.startsWith(ORIGIN_TAG)) {
                    Feature feature = new Feature();
                    feature.setType(line.substring(5, 20).trim());
                    feature.setLocation(line.substring(21, line.length()).trim());

                    String propName = null;
                    line = lineIter.next();
                    processFeatureQualifier(line, propName, feature, lineIter);

                    while (!line.startsWith(ORIGIN_TAG) && StringUtils.isEmpty(line.substring(5, 20).trim())) {
                        line = lineIter.next();
                        processFeatureQualifier(line, propName, feature, lineIter);
                    }

                    info.getFeatures().add(feature);
                }

            }

        }

        return info;
    }

    private void processFeatureQualifier(String line, String propName, Feature feature, Iterator<String> lineIter) {
        if (line.trim().startsWith("/") && line.trim().contains("=")) {
            propName = line.trim().split("=")[0];
            propName = propName.substring(1, propName.length());

            if (!line.trim().contains("\"")) {
                // probably numeric
                feature.getQualifiers().setProperty(propName, line.trim().split("=")[1]);
                return;
            }

            if (line.trim().endsWith("\"")) {
                // single line string value
                feature.getQualifiers().setProperty(propName, line.trim().split("=")[1].replace("\"", ""));
                return;
            }

            if (!line.trim().endsWith("\"")) {
                // multiline value
                StringBuilder sb = new StringBuilder(String.format("%s%n", line.trim().split("=")[1]));
                do {
                    line = lineIter.next();
                    sb.append(String.format("%s%n", line.trim()));
                } while (!line.trim().endsWith("\""));
                feature.getQualifiers().setProperty(propName, sb.toString().replace("\"", ""));
            }

        }

    }

}
