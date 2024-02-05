package com.example.tms.Controller;

import com.example.tms.DTO.UserDTO;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.User;
import com.example.tms.Service.JwtService;
import com.example.tms.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public CustomResponseEntity<?> registerUser(@RequestBody UserDTO userDto){
        try {
            return userService.createUser(userDto);
        }catch (DataIntegrityViolationException dive){
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
    }

    @PostMapping("/authenticate")
    public CustomResponseEntity<?> authenticateUser(@RequestBody User user){
        return userService.authenticateUser(user);
    }

    @GetMapping("/info")
    public CustomResponseEntity<?> getUserInfo(HttpServletRequest http){
        return userService.getByUsername(jwtService.extractUsernameFromRequestHeader(http));
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<?> getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

}
