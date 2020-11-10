package pl.waw.mrn.kudu.sample.app.dao.repository;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("kudu")
public class KuduProperties {
    private String masterAddresses;
    private String schema;
}
