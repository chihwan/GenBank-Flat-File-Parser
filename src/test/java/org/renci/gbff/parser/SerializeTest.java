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
        List<Sequence> results = gbffMgr.deserialize(new File("/tmp", "single.gb.gz"));
        assertTrue(results != null);
        assertTrue(results.size() == 1);
        gbffMgr.serialize(new File("/tmp", "asdf.gz"), results);
    }

}
