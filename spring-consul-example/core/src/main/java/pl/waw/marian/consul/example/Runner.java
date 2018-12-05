package pl.waw.marian.consul.example;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Runner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Runner.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }
}
