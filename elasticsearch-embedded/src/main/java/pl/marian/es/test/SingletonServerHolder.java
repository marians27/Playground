package pl.marian.es.test;

import org.elasticsearch.node.NodeValidationException;

import java.io.IOException;

public class SingletonServerHolder {

    private static final ElasticSearchEmbeddedServer SERVER = ElasticSearchEmbeddedServer.createSimpleServer();

    private static volatile boolean started;

    public static synchronized void start() throws NodeValidationException {
        if (!started) {
            SERVER.startNode();
            started = true;
        }
    }

    public static ElasticSearchEmbeddedServer getServer() {
        return SERVER;
    }

    public static void close() {
        try {
            SERVER.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
