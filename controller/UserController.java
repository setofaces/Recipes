package recipes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import recipes.entity.User;
import recipes.service.UserServiceImpl;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl service) {
        this.userService = service;
    }

    @PostMapping(path = "/api/register", consumes = APPLICATION_JSON_VALUE)
    public void register(@Valid @RequestBody User user) {
        userService.registerNewUser(user.getEmail(), user.getPassword());
    }


}
