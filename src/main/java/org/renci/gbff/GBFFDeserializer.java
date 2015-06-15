package org.renci.gbff;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.renci.gbff.model.Comment;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Origin;
import org.renci.gbff.model.Sequence;
import org.renci.gbff.model.Source;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GBFFDeserializer implements Callable<Sequence>, Constants {

    private final Logger logger = LoggerFactory.getLogger(GBFFDeserializer.class);

    private LinkedList<String> lines;

    private Filter filter;

    public GBFFDeserializer(LinkedList<String> lines, Filter filter) {
        super();
        this.lines = lines;
        this.filter = filter;
    }

    @Override
    public Sequence call() throws Exception {
        Sequence sequence = new Sequence();

        Iterator<String> lineIter = lines.iterator();
        while (lineIter.hasNext()) {
            String line = lineIter.next().trim();
            if (line.startsWith(LOCUS_TAG)) {
                sequence.setLocus(line.substring(12, line.length()));
            }

            if (line.startsWith(DEFINITION_TAG)) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s%n", line.substring(12, line.length())));
                while (!line.trim().endsWith(".")) {
                    line = lineIter.next();
                    sb.append(String.format("%s%n", line));
                }
                sequence.setDefinition(sb.toString().trim());
            }

            if (line.startsWith(ACCESSION_TAG)) {
                String accession = line.substring(12, line.length());
                sequence.setAccession(accession);
            }

            if (line.startsWith(VERSION_TAG)) {
                sequence.setVersion(line.substring(12, line.length()));
            }

            if (line.startsWith(KEYWORDS_TAG)) {
                sequence.setKeywords(line.substring(12, line.length()));
            }

            if (line.startsWith(SOURCE_TAG)) {
                Source source = new Source();
                source.setDescription(line.substring(12, line.length()));
                sequence.setSource(source);
            }

            if (line.startsWith(ORGANISM_TAG)) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s%n", line.substring(10, line.length())));
                do {
                    line = lineIter.next().trim();
                    sb.append(String.format("%s%n", line));
                } while (!line.endsWith("."));
                sequence.getSource().setOrganism(sb.toString().trim());
            }

            if (line.startsWith(COMMENT_TAG)) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%s%n", line.substring(12, line.length())));
                do {
                    line = lineIter.next().trim();
                    sb.append(String.format("%s%n", line));
                } while (!line.trim().endsWith("."));
                Comment comment = new Comment();
                comment.setDescription(sb.toString().trim());

                while (!line.startsWith(FEATURES_TAG)) {
                    line = lineIter.next().trim();
                    if (line.startsWith("##Genome-Annotation-Data-START##")) {

                        String key = null;
                        do {
                            line = lineIter.next().trim();
                            if (line.contains("::")) {
                                StringTokenizer st = new StringTokenizer(line, "::");
                                key = st.nextToken().trim();
                                comment.getGenomeAnnotations().setProperty(key, st.nextToken().trim());
                            } else {
                                comment.getGenomeAnnotations().setProperty(key,
                                        String.format("%s %s", comment.getGenomeAnnotations().getProperty(key), line));
                            }
                        } while (!line.startsWith("##Genome-Annotation-Data-END##"));
                    }
                }
                sequence.setComment(comment);
            }

            if (line.startsWith(FEATURES_TAG)) {
                line = lineIter.next();
                while (!line.startsWith(ORIGIN_TAG)) {
                    Feature feature = new Feature();
                    feature.setType(line.substring(5, 20).trim());
                    feature.setLocation(line.substring(21, line.length()).trim());

                    String propName = null;
                    line = lineIter.next();
                    processFeatureQualifier(line.trim(), propName, feature, lineIter);

                    while (!line.startsWith(ORIGIN_TAG) && StringUtils.isEmpty(line.substring(5, 20).trim())) {
                        line = lineIter.next();
                        processFeatureQualifier(line.trim(), propName, feature, lineIter);
                    }

                    sequence.getFeatures().add(feature);
                }
            }

            if (line.startsWith(ORIGIN_TAG)) {
                while (lineIter.hasNext()) {
                    line = lineIter.next().trim();
                    StringTokenizer st = new StringTokenizer(line, " ");
                    Origin origin = new Origin();
                    origin.setIndex(Integer.valueOf(st.nextToken()));
                    StringBuilder sb = new StringBuilder();
                    while (st.hasMoreTokens()) {
                        sb.append(st.nextToken());
                    }
                    origin.setSequence(sb.toString());
                    sequence.getOrigin().add(origin);
                }
            }
        }

        if (filter != null && !filter.accept(sequence)) {
            return null;
        }
        logger.debug(sequence.toString());

        return sequence;
    }

    private void processFeatureQualifier(String line, String propName, Feature feature, Iterator<String> lineIter) {
        if (line.startsWith("/") && line.contains("=")) {
            propName = line.split("=")[0];
            propName = propName.substring(1, propName.length());

            if (!line.contains("\"")) {
                // probably numeric
                feature.getQualifiers().setProperty(propName, line.split("=")[1]);
                return;
            }

            if (line.endsWith("\"")) {
                // single line string value
                feature.getQualifiers().setProperty(propName, line.split("=")[1].replace("\"", ""));
                return;
            }

            if (!line.endsWith("\"")) {
                // multiline value
                StringBuilder sb = new StringBuilder(String.format("%s%n", line.split("=")[1]));
                do {
                    line = lineIter.next();
                    sb.append(String.format("%s%n", line.trim()));
                } while (!line.trim().endsWith("\""));
                feature.getQualifiers().setProperty(propName, sb.toString().trim().replace("\"", ""));
            }

        }

    }

}
