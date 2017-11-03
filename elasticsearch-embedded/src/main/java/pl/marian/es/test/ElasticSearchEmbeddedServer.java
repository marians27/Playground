package pl.marian.es.test;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeValidationException;
import org.elasticsearch.transport.Netty3Plugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import static java.util.Collections.singletonList;

public class ElasticSearchEmbeddedServer {

    private final Node elasticSearchEmbeddedNode;

    private final Path elasticSearchDirectory;

    private ElasticSearchEmbeddedServer(Function<String, Node> nodeCreator) throws IOException {
        elasticSearchDirectory = Files.createTempDirectory("elasticsearch-data");
        this.elasticSearchEmbeddedNode = nodeCreator.apply(elasticSearchDirectory.toString());
    }

    public void startNode() throws NodeValidationException {
        elasticSearchEmbeddedNode.start();
    }

    public void close() throws IOException {
        elasticSearchEmbeddedNode.close();
        FileUtils.deleteDirectory(elasticSearchDirectory.toFile());
    }

    public Client getClient() {
        return elasticSearchEmbeddedNode.client();
    }

    public static ElasticSearchEmbeddedServer createSimpleServer() {
        return create(ElasticSearchEmbeddedServer::simpleNode);
    }

    public static ElasticSearchEmbeddedServer createHttpEnabledServer() {
        return create(ElasticSearchEmbeddedServer::httpEnabledNode);
    }

    private static ElasticSearchEmbeddedServer create(Function<String, Node> nodeCreator) {
        try {
            return new ElasticSearchEmbeddedServer(nodeCreator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Node simpleNode(String dataFilePath) {
        Settings settings = Settings.builder()
                .put("http.enabled", "false")
                .put("transport.type", "local")
                .put("path.home", dataFilePath)
                .build();

        return new Node(settings);
    }

    private static Node httpEnabledNode(String dataFilePath) {
        Settings settings = Settings.builder()
                .put("http.enabled", "true")
                .put("http.type", "netty3")
                .put("transport.type", "local")
                .put("path.home", dataFilePath)
                .build();

        return new Netty3PluginNode(settings);
    }

    private static class Netty3PluginNode extends Node {
        private Netty3PluginNode(Settings settings) {
            super(InternalSettingsPreparer.prepareEnvironment(settings, null),
                    singletonList(Netty3Plugin.class));
        }
    }

}
