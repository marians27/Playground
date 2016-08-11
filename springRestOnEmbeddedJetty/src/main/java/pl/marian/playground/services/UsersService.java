package pl.marian.playground.services;

import org.springframework.stereotype.Service;

@Service
public class UsersService {

    public String getUserById(Integer userId) {
        return "{\"userId\":" + userId + "}";
    }
}
