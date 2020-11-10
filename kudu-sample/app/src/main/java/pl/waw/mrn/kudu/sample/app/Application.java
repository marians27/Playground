package pl.waw.mrn.kudu.sample.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.waw.mrn.kudu.sample.app.dao.config.RepositoryConfig;

@SpringBootApplication
@Import(RepositoryConfig.class)
@EnableWebMvc
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
