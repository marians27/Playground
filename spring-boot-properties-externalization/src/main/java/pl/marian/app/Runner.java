package pl.marian.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Runner {

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    @PostConstruct
    public void logLoading() {
        LOGGER.info("Runner config loaded (SpringBootApplication)");
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Runner.class, args);
    }
}
