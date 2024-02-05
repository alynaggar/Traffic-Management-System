package com.example.tms.Service;

import com.example.tms.DTO.UserDTO;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.Role;
import com.example.tms.Entity.User;
import com.example.tms.Repository.RoleRepository;
import com.example.tms.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepo, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepo;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public CustomResponseEntity<?> authenticateUser(User user) {
        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        if (user1.isPresent()) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, jwtService.generateToken(user1.get()));
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    @Transactional
    public CustomResponseEntity<?> createUser(UserDTO userDto) {
        Optional<Role> role = roleRepository.findById(userDto.getRole());
        if(role.isPresent()) {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRole(role.get());
            userRepository.save(user);
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, jwtService.generateToken(user));
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    public CustomResponseEntity<?> getByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, user.get());
    }

    public CustomResponseEntity<?> getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()) {
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, user.get());
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }
}
