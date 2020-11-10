package pl.waw.mrn.kudu.sample.app.dao.repository;

import org.apache.kudu.client.KuduClient;
import org.apache.kudu.client.KuduClient.KuduClientBuilder;

public class KuduClientProvider {
    private final KuduClient kuduClient;
    private final KuduProperties kuduProperties;

    public KuduClientProvider(KuduProperties kuduProperties) {
        this.kuduProperties = kuduProperties;
        this.kuduClient = new KuduClientBuilder(kuduProperties.getMasterAddresses())
                .build();
    }

    public KuduClient provide() {
        return kuduClient;
    }

    public String tableNameConverter(String tableName) {
        return "impala::" + kuduProperties.getSchema() + "." + tableName;
    }
}
