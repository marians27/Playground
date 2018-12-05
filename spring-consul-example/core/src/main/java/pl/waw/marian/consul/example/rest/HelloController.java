package pl.waw.marian.consul.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.waw.marian.consul.example.service.GreetingService;

@RestController
public class HelloController {

    @Autowired
    private GreetingService greetingService;

    @RequestMapping
    public String greeting() {
        return greetingService.loadGreeting();
    }
}
