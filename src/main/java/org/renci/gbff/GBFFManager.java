package org.renci.gbff;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.renci.gbff.model.Sequence;

public class GBFFManager {

    private static GBFFManager instance;

    public static GBFFManager getInstance() {
        if (instance == null) {
            instance = new GBFFManager();
        }
        return instance;
    }

    private GBFFManager() {
        super();
    }

    public List<Sequence> deserialize(final File... gbFiles) {
        return deserialize(null, gbFiles);
    }

    public List<Sequence> deserialize(final GBFFFilter filter, final File... gbFiles) {
        return deserialize(filter, 2, false, gbFiles);
    }

    public List<Sequence> deserialize(final GBFFFilter filter, int threads, boolean skipOrigin, final File... gbFiles) {
        ExecutorService es = Executors.newFixedThreadPool(threads);
        List<Sequence> ret = new ArrayList<Sequence>();
        List<Future<List<Sequence>>> futures = new ArrayList<Future<List<Sequence>>>();
        try {
            for (File f : gbFiles) {
                futures.add(es.submit(new GBFFDeserializer(f, filter, skipOrigin)));
            }
            es.shutdown();
            if (!es.awaitTermination(5L, TimeUnit.MINUTES)) {
                es.shutdownNow();
            }
            for (Future<List<Sequence>> future : futures) {
                ret.addAll(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public void serialize(File gbFile, List<Sequence> sequences) {
        GBFFSerializer serializer = new GBFFSerializer(gbFile, sequences);
        serializer.run();
    }

}
