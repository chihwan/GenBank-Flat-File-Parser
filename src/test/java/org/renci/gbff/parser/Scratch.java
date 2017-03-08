package org.renci.gbff.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.renci.gbff.GBFFFilter;
import org.renci.gbff.GBFFManager;
import org.renci.gbff.filter.GBFFAndFilter;
import org.renci.gbff.filter.GBFFFeatureSourceOrganismNameFilter;
import org.renci.gbff.filter.GBFFFeatureTypeNameFilter;
import org.renci.gbff.filter.GBFFSequenceAccessionPrefixFilter;
import org.renci.gbff.filter.GBFFSourceOrganismNameFilter;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Sequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Scratch {

    private final Logger logger = LoggerFactory.getLogger(Scratch.class);

    @Test
    public void testTE() {
        List<String> testables = Arrays.asList("(pos:262..264,aa:OTHER)", "(pos:556..558,aa:OTHER)",
                "(pos:1204..1206,aa:OTHER)");

        Pattern translationExceptionPattern = Pattern
                .compile("\\(pos:(?<start>\\d+)\\.+(?<stop>\\d+)\\,aa:(?<aminoAcid>[a-zA-Z]+)\\)");

        for (String test : testables) {
            Matcher m = translationExceptionPattern.matcher(test);
            if (m.find()) {
                System.out.println(m.group("start"));
                System.out.println(m.group("stop"));
                System.out.println(m.group("aminoAcid"));
            }
        }

    }

    @Test
    public void testLocations() {
        String expression = "join(114..317,319..801)";
        // expression = "complement(join(2691..4571,4918..5163))";
        // expression = "join(complement(4918..5163),complement(2691..4571))";

        Pattern p = Pattern.compile("^(join|order)\\((.+)\\)$");
        Matcher m = p.matcher(expression);
        m.find();
        String joinContent = m.group(2);
        List<Integer> positions = new ArrayList<Integer>();
        String[] ranges = joinContent.split(",");
        for (String range : ranges) {
            String[] split = range.split("\\.\\.");
            Integer start = Integer.valueOf(split[0]);
            Integer end = Integer.valueOf(split[1]);
            positions.add(start);
            positions.add(end);
        }

        Collections.sort(positions);

        assertTrue(positions.get(0) == 114);
        assertTrue(positions.get(positions.size() - 1) == 801);

    }

    public void findComplexLocations() {
        GBFFManager parser = GBFFManager.getInstance(8, true);
        List<String> acceptablePrefixList = Arrays.asList(new String[] { "NM_" });
        List<GBFFFilter> filters = Arrays
                .asList(new GBFFFilter[] { new GBFFSequenceAccessionPrefixFilter(acceptablePrefixList),
                        new GBFFSourceOrganismNameFilter("Homo sapiens"),
                        new GBFFFeatureSourceOrganismNameFilter("Homo sapiens"), new GBFFFeatureTypeNameFilter("CDS"),
                        new GBFFFeatureTypeNameFilter("source") });

        GBFFAndFilter filter = new GBFFAndFilter(filters);
        List<File> ret = new ArrayList<File>();

        File tmpDir = new File("/tmp");
        for (File f : tmpDir.listFiles()) {
            if (f.getName().startsWith("vertebrate_mammalian")) {
                ret.add(f);
            }
        }

        for (File f : ret) {
            List<Sequence> results = parser.deserialize(filter, f);
            for (Sequence sequence : results) {
                List<Feature> features = sequence.getFeatures();
                for (Feature feature : features) {
                    if (feature.getType().equals("CDS")) {
                        if (feature.getLocation().contains("complement") || feature.getLocation().contains("join")) {
                            logger.warn(f.getAbsolutePath());
                            logger.warn(sequence.toString());
                            logger.warn(feature.toString());
                        }
                    }
                }
            }
        }
    }

}
