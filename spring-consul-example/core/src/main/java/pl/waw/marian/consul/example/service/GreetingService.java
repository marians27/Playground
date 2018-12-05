package pl.waw.marian.consul.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@RefreshScope
public class GreetingService {

    @Value("${microdev.greeting.full}")
    private String greetingValue;

    @Autowired
    private Environment env;

    public String loadGreeting() {
        return greetingValue;
    }
}
