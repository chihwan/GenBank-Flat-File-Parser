package org.renci.gbff.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.renci.gbff.parser.model.Feature;
import org.renci.gbff.parser.model.GenBankInfo;

public class Scratch {

    @Test
    public void testSingle() {
        GenBankParser parser = GenBankParser.getInstance();
        long start = System.currentTimeMillis();
        List<GenBankInfo> results = parser.parse(new File("/tmp", "single.gb.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() == 1);
        System.out.println(String.format("%d millis", end - start));
        System.out.println(results.get(0).toString());
        for (Feature feature : results.get(0).getFeatures()) {
            System.out.println(feature.toString());
            for (Object key : feature.getQualifiers().keySet()) {
                System.out.println(String.format("%s = %s", key, feature.getQualifiers().getProperty(key.toString())));
            }
        }
    }

    @Test
    public void testMultiple() {
        GenBankParser parser = GenBankParser.getInstance();
        long start = System.currentTimeMillis();
        List<GenBankInfo> results = parser.parse(new File("/tmp", "vertebrate_mammalian.1.rna.gbff.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() > 1);
        System.out.println(String.format("%d records", results.size()));
        System.out.println(String.format("%d millis", end - start));
        // for (GenBankInfo info : results) {
        // System.out.println(info.toString());
        // }
    }

}
