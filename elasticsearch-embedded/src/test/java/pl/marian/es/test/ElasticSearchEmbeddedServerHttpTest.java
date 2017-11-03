package pl.marian.es.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

public class ElasticSearchEmbeddedServerHttpTest {

    private ElasticSearchEmbeddedServer embeddedServer = ElasticSearchEmbeddedServer.createHttpEnabledServer();

    @Before
    public void startNode() throws Exception {
        embeddedServer.startNode();
    }

    @After
    public void closetNode() throws Exception {
        embeddedServer.close();
    }

    @Test
    public void httpConnectionWorking() throws Exception {
        URL obj = new URL("http://localhost:9200/_cat/health");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        int responseCode = con.getResponseCode();
        String[] parsedResult = parseResponse(con);

        assertThat(responseCode).isEqualTo(200);
        assertThat(parsedResult[3]).isEqualTo("green");
    }

    private String[] parseResponse(HttpURLConnection connection) throws IOException {
        try (InputStream in1 = connection.getInputStream();
             InputStreamReader in2 = new InputStreamReader(in1);
             BufferedReader bufferedReader = new BufferedReader(in2)) {

            return bufferedReader.lines()
                    .map(result -> result.split("\\s+"))
                    .findFirst()
                    .orElse(null);
        }
    }
}
