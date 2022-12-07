package com.example.userservice.module.controller;

import com.example.userservice.module.controller.dto.RequestUser;
import com.example.userservice.module.dto.UserDto;
import com.example.userservice.module.service.UserService;
import com.example.userservice.module.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class UserController {

    private final Environment env;
    private final Greeting greeting;
    private final UserService userService;

    @Autowired
    public UserController(Environment env, Greeting greeting, UserService userService) {
        this.env = env;
        this.greeting = greeting;
        this.userService = userService;
    }

    @PostMapping("/users")
    public String createUser(@RequestBody RequestUser user) {
        UserDto serviceRequestDto = createUserDto(user);
        userService.createUser(serviceRequestDto);
        return "Create user method is called";
    }

    private UserDto createUserDto(RequestUser user) {
        UserDto serviceRequestDto = new UserDto();
        serviceRequestDto.setName(user.getName());
        serviceRequestDto.setEmail(user.getEmail());
        serviceRequestDto.setPassword(user.getPassword());
        return serviceRequestDto;
    }

    @GetMapping("/health_check")
    public String status() {
        return "It's Working in User Service";
    }


    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }
}
