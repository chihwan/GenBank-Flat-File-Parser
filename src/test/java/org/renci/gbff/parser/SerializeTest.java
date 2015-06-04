package org.renci.gbff.parser;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.Test;
import org.renci.gbff.parser.model.Sequence;

public class SerializeTest {

    @Test
    public void testSingle() {
        GBFFManager gbffMgr = GBFFManager.getInstance();
        long start = System.currentTimeMillis();
        List<Sequence> results = gbffMgr.deserialize(new File("/tmp", "single.gb.gz"));
        long end = System.currentTimeMillis();
        assertTrue(results != null);
        assertTrue(results.size() == 1);
        System.out.println(String.format("%d seconds", (end - start) / 1000));

        gbffMgr.serialize(new File("/tmp", "asdf.gz"), results);

    }

}
