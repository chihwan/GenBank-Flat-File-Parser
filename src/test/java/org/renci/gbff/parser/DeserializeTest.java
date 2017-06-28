package org.renci.gbff.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.renci.gbff.GBFFFilter;
import org.renci.gbff.GBFFManager;
import org.renci.gbff.filter.GBFFAndFilter;
import org.renci.gbff.filter.GBFFFeatureSourceOrganismNameFilter;
import org.renci.gbff.filter.GBFFFeatureTypeNameFilter;
import org.renci.gbff.filter.GBFFSequenceAccessionPrefixFilter;
import org.renci.gbff.filter.GBFFSourceOrganismNameFilter;
import org.renci.gbff.model.Sequence;

public class DeserializeTest implements Runnable {

    private static final GBFFManager gbffMgr = GBFFManager.getInstance();

    public DeserializeTest() {
        super();
    }

    @Test
    public void scratch() {

        List<GBFFFilter> filters = Arrays.asList(
                new GBFFFilter[] { new GBFFSequenceAccessionPrefixFilter(Arrays.asList(new String[] { "NM_", "XM_" })),
                        new GBFFSourceOrganismNameFilter("Homo sapiens"),
                        new GBFFFeatureSourceOrganismNameFilter("Homo sapiens"), new GBFFFeatureTypeNameFilter("CDS"),
                        new GBFFFeatureTypeNameFilter("source") });

        GBFFAndFilter gbffFilter = new GBFFAndFilter(filters);

        File f = new File(
                "/home/jdr0887/workspace/renci/canvas/primer/primer/primer-server/target/primer-server-0.0.6-SNAPSHOT/data/refseq",
                "vertebrate_mammalian.2.rna.gbff.gz");

        List<Sequence> results = gbffMgr.deserialize(gbffFilter, f);

        for (Sequence sequence : results) {
            String versionedAccession = sequence.getVersion().trim();
            System.out.println(versionedAccession.toString());
        }
    }

    @Test
    public void testSingle() {
        long start = System.currentTimeMillis();
        List<Sequence> results = gbffMgr.deserialize(new File("/tmp", "vertebrate_mammalian.286.rna.gbff.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        System.out.println(String.format("%d seconds", (end - start) / 1000));
        // for (Sequence sequence : results) {
        // System.out.println(sequence.toString());
        // for (Feature feature : sequence.getFeatures()) {
        // System.out.println(feature.toString());
        // }
        // }
    }

    @Test
    public void testMultiple() {
        long start = System.currentTimeMillis();
        List<Sequence> results = gbffMgr.deserialize(new File("/tmp", "vertebrate_mammalian.95.rna.gbff.gz"),
                new File("/tmp", "vertebrate_mammalian.100.rna.gbff.gz"));
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
        long start = System.currentTimeMillis();
        List<String> acceptablePrefixList = Arrays.asList(new String[] { "NM_" });
        List<Sequence> results = gbffMgr.deserialize(new GBFFSequenceAccessionPrefixFilter(acceptablePrefixList),
                new File("/tmp", "vertebrate_mammalian.95.rna.gbff.gz"),
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

    @Test
    public void testAndFilter() {
        long start = System.currentTimeMillis();
        List<String> acceptablePrefixList = Arrays.asList(new String[] { "NM_" });
        List<GBFFFilter> filters = Arrays
                .asList(new GBFFFilter[] { new GBFFSequenceAccessionPrefixFilter(acceptablePrefixList),
                        new GBFFSourceOrganismNameFilter("Homo sapiens"),
                        new GBFFFeatureSourceOrganismNameFilter("Homo sapiens") });
        List<Sequence> results = gbffMgr.deserialize(new GBFFAndFilter(filters),
                new File("/tmp", "vertebrate_mammalian.286.rna.gbff.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() > 1);
        for (Sequence sequence : results) {
            assertTrue(sequence.getAccession().startsWith("NM"));
            assertTrue(sequence.getSource() != null);
            assertTrue(sequence.getSource().getOrganism().contains("Homo sapiens"));
        }
        System.out.println(String.format("%d records", results.size()));
        System.out.println(String.format("%d millis", (end - start) / 1000));
    }

    @Test
    public void testPerformance() {
        long start = System.currentTimeMillis();
        List<String> acceptablePrefixList = Arrays.asList(new String[] { "NM_" });
        List<GBFFFilter> filters = Arrays
                .asList(new GBFFFilter[] { new GBFFSequenceAccessionPrefixFilter(acceptablePrefixList),
                        new GBFFSourceOrganismNameFilter("Homo sapiens"),
                        new GBFFFeatureSourceOrganismNameFilter("Homo sapiens"), new GBFFFeatureTypeNameFilter("CDS"),
                        new GBFFFeatureTypeNameFilter("source") });

        List<File> ret = new ArrayList<File>();

        File tmpDir = new File("/tmp");
        for (File f : tmpDir.listFiles()) {
            if (f.getName().startsWith("vertebrate_mammalian")) {
                ret.add(f);
            }
        }

        List<Sequence> results = gbffMgr.deserialize(new GBFFAndFilter(filters), 4, true,
                ret.toArray(new File[ret.size()]));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() > 1);
        System.out.println(String.format("%d records", results.size()));
        System.out.println(String.format("%d seconds", (end - start) / 1000));
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        List<String> acceptablePrefixList = Arrays.asList(new String[] { "NM_" });
        List<GBFFFilter> filters = Arrays
                .asList(new GBFFFilter[] { new GBFFSequenceAccessionPrefixFilter(acceptablePrefixList),
                        new GBFFSourceOrganismNameFilter("Homo sapiens"),
                        new GBFFFeatureSourceOrganismNameFilter("Homo sapiens"), new GBFFFeatureTypeNameFilter("CDS"),
                        new GBFFFeatureTypeNameFilter("source") });

        List<File> ret = new ArrayList<File>();

        File tmpDir = new File("/tmp");
        for (File f : tmpDir.listFiles()) {
            if (f.getName().startsWith("vertebrate_mammalian")) {
                ret.add(f);
            }
        }

        List<Sequence> results = gbffMgr.deserialize(new GBFFAndFilter(filters), 4, true,
                ret.toArray(new File[ret.size()]));
        long end = System.currentTimeMillis();
        System.out.println(String.format("%d records", results.size()));
        System.out.println(String.format("%d seconds", (end - start) / 1000));
    }

    public static void main(String[] args) {
        DeserializeTest runnable = new DeserializeTest();
        runnable.run();
    }

}
