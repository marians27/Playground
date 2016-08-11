package pl.marian.playground.init.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "pl.marian.playground.services")
public class ApplicationConfig {

}
