package pl.marian.app.base;

import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.marian.app.config.DataSourceConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataSourceConfig.class)
@EnableConfigurationProperties
@TestPropertySource(locations = "classpath:test.properties")
public abstract class TestBase {
}
