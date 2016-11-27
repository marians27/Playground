package pl.marian.app.repository;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Repository
public class SecondaryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryRepository.class);

    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource dataSource;

    @PostConstruct
    public void logDataBase() {
        if (dataSource instanceof HikariDataSource) {
            HikariDataSource hikariDataSource = (HikariDataSource) dataSource;
            LOGGER.info("Using main data source with user {}", hikariDataSource.getUsername());
        } else {
            LOGGER.info("Using main data source {}", dataSource);
        }
    }
}
