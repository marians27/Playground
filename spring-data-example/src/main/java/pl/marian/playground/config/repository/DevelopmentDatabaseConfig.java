package pl.marian.playground.config.repository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import pl.marian.playground.config.Profiles;

import javax.sql.DataSource;

@Configuration
@Profile(Profiles.DEV)
public class DevelopmentDatabaseConfig {

    @Bean
    public Boolean generateDdl() {
        return true;
    }

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).build();
    }
}
