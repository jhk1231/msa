package com.example.userservice.module.controller;

import com.example.userservice.module.controller.dto.RequestUser;
import com.example.userservice.module.controller.dto.ResponseUser;
import com.example.userservice.module.dto.UserDto;
import com.example.userservice.module.service.UserService;
import com.example.userservice.module.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     * 회원가입
     * @param user
     * @return
     */
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        final UserDto serviceRequestDto = createUserDto(user);
        final UserDto serviceResponseDto = userService.createUser(serviceRequestDto);
        final ResponseUser responseDto = createResponseUser(serviceResponseDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    private ResponseUser createResponseUser(UserDto serviceResponseDto) {
        final ResponseUser responseDto = new ResponseUser();
        responseDto.setEmail(serviceResponseDto.getEmail());
        responseDto.setUserId(serviceResponseDto.getUserId());
        responseDto.setPassword(serviceResponseDto.getPassword());
        return responseDto;
    }

    private UserDto createUserDto(RequestUser user) {
        final UserDto serviceRequestDto = new UserDto();
        serviceRequestDto.setName(user.getName());
        serviceRequestDto.setEmail(user.getEmail());
        serviceRequestDto.setPassword(user.getPassword());
        return serviceRequestDto;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %S", env.getProperty("local.server.port"));
    }


    @GetMapping("/welcome")
    public String welcome() {
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }
}
