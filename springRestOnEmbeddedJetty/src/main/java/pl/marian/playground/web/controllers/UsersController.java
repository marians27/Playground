package pl.marian.playground.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.marian.playground.services.UsersService;

@RestController
@RequestMapping(path = "/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public String getUserById(@PathVariable Integer userId) {
        return usersService.getUserById(userId);
    }
}
