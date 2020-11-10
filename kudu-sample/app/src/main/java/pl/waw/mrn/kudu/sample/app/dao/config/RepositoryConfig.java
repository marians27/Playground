package pl.waw.mrn.kudu.sample.app.dao.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.waw.mrn.kudu.sample.app.dao.repository.KuduClientProvider;
import pl.waw.mrn.kudu.sample.app.dao.repository.KuduProperties;
import pl.waw.mrn.kudu.sample.app.dao.repository.UserInfoRepository;

@Configuration
@EnableConfigurationProperties(KuduProperties.class)
public class RepositoryConfig {

    @Bean
    public UserInfoRepository userInfoRepository(KuduClientProvider kuduClientProvider) {
        return new UserInfoRepository(
                kuduClientProvider::provide,
                kuduClientProvider::tableNameConverter);
    }

    @Bean
    public KuduClientProvider kuduClientProvider(KuduProperties kuduProperties) {
        return new KuduClientProvider(kuduProperties);
    }
}
