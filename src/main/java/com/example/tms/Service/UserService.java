package com.example.tms.Service;

import com.example.tms.Entity.Response.ResponseCode;
import com.example.tms.Entity.Response.ResponseEntity;
import com.example.tms.Entity.User;
import com.example.tms.Repository.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> authenticateUser(User user) {
        Optional<User> user1 = userRepo.findByUsername(user.getUsername());
        if (user1.isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return new ResponseEntity<>(ResponseCode.SUCCESS, jwtService.generateToken(user1.get()));
        }
        return new ResponseEntity<>(ResponseCode.NOT_FOUND);
    }

    public ResponseEntity<?> createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(ResponseCode.SUCCESS, jwtService.generateToken(user));
    }
}
