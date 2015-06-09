package org.renci.gbff.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.renci.gbff.GBFFManager;
import org.renci.gbff.filter.SequenceAccessionPrefixFilter;
import org.renci.gbff.model.Feature;
import org.renci.gbff.model.Origin;
import org.renci.gbff.model.Sequence;

public class DeserializeTest {

    @Test
    public void testSingle() {
        GBFFManager parser = GBFFManager.getInstance();
        long start = System.currentTimeMillis();
        List<Sequence> results = parser.deserialize(new File("/tmp", "single.gb.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() == 1);
        System.out.println(String.format("%d seconds", (end - start) / 1000));
        System.out.println(results.get(0).toString());
        System.out.printf("ORGANISM: %s%n", results.get(0).getSource().getOrganism());
        for (Feature feature : results.get(0).getFeatures()) {
            System.out.println(feature.toString());
            for (Object key : feature.getQualifiers().keySet()) {
                System.out.println(String.format("%s = %s", key, feature.getQualifiers().getProperty(key.toString())));
            }
        }
        for (Origin origin : results.get(0).getOrigin()) {
            System.out.println(origin.toString());
        }
    }

    @Test
    public void testMultiple() {
        GBFFManager parser = GBFFManager.getInstance();
        long start = System.currentTimeMillis();
        List<Sequence> results = parser.deserialize(new File("/tmp", "vertebrate_mammalian.95.rna.gbff.gz"), new File(
                "/tmp", "vertebrate_mammalian.100.rna.gbff.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() > 1);
        for (Sequence info : results) {
            System.out.println(info.toString());
        }
        System.out.println(String.format("%d records", results.size()));
        System.out.println(String.format("%d millis", (end - start) / 1000));
    }

    @Test
    public void testFilter() {
        GBFFManager parser = GBFFManager.getInstance();
        long start = System.currentTimeMillis();
        List<String> acceptablePrefixList = Arrays.asList(new String[] { "NM_" });
        List<Sequence> results = parser.deserialize(new SequenceAccessionPrefixFilter(acceptablePrefixList), new File(
                "/tmp", "vertebrate_mammalian.95.rna.gbff.gz"),
                new File("/tmp", "vertebrate_mammalian.100.rna.gbff.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() > 1);
        for (Sequence sequence : results) {
            System.out.println(sequence.toString());
            assertTrue(sequence.getAccession().startsWith("NM"));
        }
        System.out.println(String.format("%d records", results.size()));
        System.out.println(String.format("%d millis", (end - start) / 1000));
    }
}
