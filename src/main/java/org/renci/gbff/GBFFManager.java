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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GBFFManager {

    private final Logger logger = LoggerFactory.getLogger(GBFFManager.class);

    private final int threads;

    private static GBFFManager instance;

    public static GBFFManager getInstance(int threads) {
        if (instance == null) {
            instance = new GBFFManager(threads);
        }
        return instance;
    }

    public static GBFFManager getInstance() {
        if (instance == null) {
            instance = new GBFFManager(2);
        }
        return instance;
    }

    private GBFFManager(int threads) {
        super();
        this.threads = threads;
    }

    public List<Sequence> deserialize(final File... gbFiles) {
        return deserialize(null, gbFiles);
    }

    public List<Sequence> deserialize(final Filter filter, final File... gbFiles) {
        ExecutorService es = Executors.newFixedThreadPool(threads);
        List<Sequence> ret = new ArrayList<Sequence>();
        List<Future<List<Sequence>>> futures = new ArrayList<Future<List<Sequence>>>();
        try {
            for (File f : gbFiles) {
                futures.add(es.submit(new GBFFDeserializer(f, filter)));
            }
            es.shutdown();
            es.awaitTermination(5L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Future<List<Sequence>> future : futures) {
            try {
                ret.addAll(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        logger.info("ret.size(): {}", ret.size());
        return ret;
    }

    public void serialize(File gbFile, List<Sequence> sequences) {
        GBFFSerializer serializer = new GBFFSerializer(gbFile, sequences);
        serializer.run();
    }

}
