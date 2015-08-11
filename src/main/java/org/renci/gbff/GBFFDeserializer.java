package org.renci.gbff;

import static org.renci.gbff.Constants.ACCESSION_TAG;
import static org.renci.gbff.Constants.COMMENT_TAG;
import static org.renci.gbff.Constants.DEFINITION_TAG;
import static org.renci.gbff.Constants.FEATURES_TAG;
import static org.renci.gbff.Constants.KEYWORDS_TAG;
import static org.renci.gbff.Constants.LOCUS_TAG;
import static org.renci.gbff.Constants.ORGANISM_TAG;
import static org.renci.gbff.Constants.ORIGIN_TAG;
import static org.renci.gbff.Constants.SOURCE_TAG;
import static org.renci.gbff.Constants.VERSION_TAG;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.lang3.StringUtils;
import org.renci.gbff.model.Comment;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Origin;
import org.renci.gbff.model.Sequence;
import org.renci.gbff.model.Source;

public class GBFFDeserializer implements Callable<List<Sequence>> {

    private final File inputFile;

    private final GBFFFilter filter;

    private final boolean skipOrigin;

    public GBFFDeserializer(final File inputFile, final GBFFFilter filter) {
        super();
        this.inputFile = inputFile;
        this.filter = filter;
        this.skipOrigin = false;
    }

    public GBFFDeserializer(File inputFile, GBFFFilter filter, boolean skipOrigin) {
        super();
        this.inputFile = inputFile;
        this.filter = filter;
        this.skipOrigin = skipOrigin;
    }

    @Override
    public List<Sequence> call() throws Exception {
        List<Sequence> ret = new ArrayList<Sequence>();

        try (FileInputStream fin = new FileInputStream(inputFile);
                BufferedInputStream in = new BufferedInputStream(fin);
                GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
                InputStreamReader isr = new InputStreamReader(gzIn);
                BufferedReader br = new BufferedReader(isr)) {
            LinkedList<String> lines = new LinkedList<String>();
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("//")) {
                    Sequence info = parseInputFile(lines);
                    if (info != null) {
                        ret.add(info);
                    }
                    lines.clear();
                }
                lines.add(String.format("%s%n", line));
            }
        } catch (Exception e) {
            System.out.printf("inputFile: %s%n", inputFile.getAbsolutePath());
            e.printStackTrace();
        }

        // try (FileInputStream fis = new FileInputStream(inputFile);
        // GZIPInputStream gis = new GZIPInputStream(fis);
        // InputStreamReader isr = new InputStreamReader(gis);
        // BufferedReader br = new BufferedReader(isr)) {
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        System.gc();
        return ret;
    }

    private Sequence parseInputFile(LinkedList<String> lines) {
        Sequence sequence = new Sequence();

        StringBuilder sb = new StringBuilder();

        Iterator<String> lineIter = lines.iterator();
        while (lineIter.hasNext()) {
            String line = lineIter.next().trim();
            if (line.startsWith(LOCUS_TAG)) {
                sequence.setLocus(line.substring(12, line.length()));
            }

            sb.delete(0, sb.length());
            if (line.startsWith(DEFINITION_TAG)) {
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

            sb.delete(0, sb.length());
            if (line.startsWith(ORGANISM_TAG)) {
                sb.append(String.format("%s%n", line.substring(10, line.length())));
                do {
                    line = lineIter.next().trim();
                    sb.append(String.format("%s%n", line));
                } while (!line.endsWith("."));
                sequence.getSource().setOrganism(sb.toString().trim());
            }

            sb.delete(0, sb.length());
            if (line.startsWith(COMMENT_TAG)) {
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
                                comment.getGenomeAnnotations().put(key, st.nextToken().trim());
                            } else {
                                comment.getGenomeAnnotations().put(key,
                                        String.format("%s %s", comment.getGenomeAnnotations().get(key), line));
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
                    sb.delete(0, sb.length());

                    String locationValue = line.substring(21, line.length()).trim();
                    if (locationValue.startsWith("join") || locationValue.startsWith("order")) {
                        sb.append(locationValue);
                        while (!line.trim().endsWith(")")) {
                            line = lineIter.next().trim();
                            sb.append(String.format("%s", line));
                        }
                    } else {
                        sb.append(locationValue);
                    }

                    feature.setLocation(sb.toString());

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

            if (!skipOrigin) {
                sb.delete(0, sb.length());
                if (line.startsWith(ORIGIN_TAG)) {
                    while (lineIter.hasNext()) {
                        line = lineIter.next().trim();
                        StringTokenizer st = new StringTokenizer(line, " ");
                        Origin origin = new Origin();
                        origin.setIndex(Integer.valueOf(st.nextToken()));
                        while (st.hasMoreTokens()) {
                            sb.append(st.nextToken());
                        }
                        origin.setSequence(sb.toString());
                        sequence.getOrigin().add(origin);
                    }
                }
            }
        }

        if (filter != null && !filter.accept(sequence)) {
            return null;
        }
        return sequence;
    }

    private void processFeatureQualifier(String line, String propName, Feature feature, Iterator<String> lineIter) {

        if (line.startsWith("/db_xref") && line.endsWith("\"")) {
            String value = line.split("=")[1].replace("\"", "").replace("\n", " ");
            String[] valueSplit = value.split(":");
            feature.getDBXrefs().put(valueSplit[0], valueSplit[1]);
            return;
        }

        if (line.startsWith("/") && line.contains("=")) {
            propName = line.split("=")[0];
            propName = propName.substring(1, propName.length());

            if (!line.contains("\"")) {
                // probably numeric
                String value = line.split("=")[1];
                feature.getQualifiers().put(propName, value);
                return;
            }

            if (line.endsWith("\"")) {
                // single line string value
                String value = line.split("=")[1].replace("\"", "").replace("\n", " ");
                feature.getQualifiers().put(propName, value);
                return;
            }

            if (!line.endsWith("\"")) {
                // multiline value
                StringBuilder sb = new StringBuilder(String.format("%s", line.split("=")[1]));
                do {
                    line = lineIter.next();
                    sb.append(String.format(" %s", line.trim()));
                } while (!line.trim().endsWith("\""));
                String value = sb.toString().trim().replace("\"", "");
                feature.getQualifiers().put(propName, value);
            }

        }

    }

}
