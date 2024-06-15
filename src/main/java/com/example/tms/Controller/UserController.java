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
            return userService.registerUser(userDto);
        }catch (DataIntegrityViolationException dive){
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
    }

    @PostMapping("/create")
    public CustomResponseEntity<?> createUser(@RequestBody User user){
        try {
            return userService.createUser(user);
        }catch (DataIntegrityViolationException dive){
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
    }

    @PostMapping("/update")
    public CustomResponseEntity<?> updateUser(@RequestBody User user){
        try {
            return userService.updateUser(user);
        }catch (DataIntegrityViolationException dive){
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
    }

    @PostMapping("/authenticate")
    public CustomResponseEntity<?> authenticateUser(@RequestBody User user){
        return userService.authenticateUser(user);
    }

    @PostMapping("/otp/generate")
    public CustomResponseEntity<?> generateOtp(@RequestBody User user){
        if(user.getUsername() == null){
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
        return userService.generateOtp(user.getUsername());
    }

    @PostMapping("/otp/validate")
    public CustomResponseEntity<?> checkOtp(@RequestBody User user){
        if(user.getUsername() == null || user.getOtp() == null){
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
        return userService.checkOtp(user.getOtp(), user.getUsername());
    }

    @GetMapping("/info")
    public CustomResponseEntity<?> getUserInfo(HttpServletRequest http){
        return userService.getByUsername(jwtService.extractUsernameFromRequestHeader(http));
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<?> getUserById(@PathVariable long id){
        return userService.getUserById(id);
    }

    @GetMapping("/all")
    public  CustomResponseEntity<?> getAllUsers(){
        return userService.getAllUsers();
    }

}
