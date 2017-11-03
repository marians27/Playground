package pl.marian.es.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RunWith(Parameterized.class)
public class ElasticSearchEmbeddedServerTest {

    private final ElasticSearchEmbeddedServer embeddedServer;

    public ElasticSearchEmbeddedServerTest(String testName, ElasticSearchEmbeddedServer embeddedServer) {
        this.embeddedServer = embeddedServer;
    }

    @Parameters(name = "{0}[{index}]")
    public static List<Object[]> data() {
        return asList(new Object[][]{
                {"SimpleServer", ElasticSearchEmbeddedServer.createSimpleServer()},
                {"HttpEnabledServer", ElasticSearchEmbeddedServer.createHttpEnabledServer()}}
        );
    }

    @Before
    public void startNode() throws Exception {
        embeddedServer.startNode();
    }

    @After
    public void closetNode() throws Exception {
        embeddedServer.close();
    }

    @Test
    public void happyPathTest() throws Exception {
        Client client = embeddedServer.getClient();
        client.admin().indices()
                .prepareCreate("test")
                .addMapping("users")
                .get();

        client.prepareIndex("test", "users", String.valueOf(1))
                .setSource(createDoc("Joe", 31))
                .get();

        client.prepareIndex("test", "users", String.valueOf(2))
                .setSource(createDoc("Jenny", 30))
                .get();

        client.prepareIndex("test", "users", String.valueOf(3))
                .setSource(createDoc("Tim", 1))
                .get();

        client.admin().indices().prepareRefresh("test").get();
        SearchResponse searchResponse = client
                .prepareSearch("test")
                .setQuery(QueryBuilders.rangeQuery("age").gt(10))
                .get();

        assertThat(searchResponse.getHits().totalHits).isEqualTo(2);
    }

    private static XContentBuilder createDoc(String name, int age) throws IOException {
        return jsonBuilder()
                .startObject()
                .field("name", name)
                .field("age", age)
                .endObject();
    }
}