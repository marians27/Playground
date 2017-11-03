package pl.marian.es.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class ElasticSearchEmbeddedServerCleanupTest {

    private final ElasticSearchEmbeddedServer embeddedServer;

    public ElasticSearchEmbeddedServerCleanupTest(String testName, ElasticSearchEmbeddedServer embeddedServer) {
        this.embeddedServer = embeddedServer;
    }

    @Parameters(name = "{0}[{index}]")
    public static List<Object[]> data() {
        return asList(new Object[][]{
                {"SimpleServer", ElasticSearchEmbeddedServer.createSimpleServer()},
                {"HttpEnabledServer", ElasticSearchEmbeddedServer.createHttpEnabledServer()}}
        );
    }

    @Test
    public void serverDirectoryRemovedAfterServerClosedTest() throws Exception {
        embeddedServer.startNode();

        Path dirPath = getElasticSearchDirectory();

        assertThat(dirPath).exists();
        embeddedServer.close();
        assertThat(dirPath).doesNotExist();
    }

    private Path getElasticSearchDirectory() throws NoSuchFieldException, IllegalAccessException {
        Field dirPathField = ElasticSearchEmbeddedServer.class.getDeclaredField("elasticSearchDirectory");
        dirPathField.setAccessible(true);
        return (Path) dirPathField.get(embeddedServer);
    }
}