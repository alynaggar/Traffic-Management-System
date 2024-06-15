package com.example.tms.Service;

import java.util.List;
import java.util.Random;

import com.example.tms.Configuration.EmailEvent;
import com.example.tms.DTO.UserDTO;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.Role;
import com.example.tms.Entity.User;
import com.example.tms.Repository.RoleRepository;
import com.example.tms.Repository.UserRepository;
import org.springframework.context.ApplicationEventPublisher;
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
    private ApplicationEventPublisher eventPublisher;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.eventPublisher = eventPublisher;
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
    public CustomResponseEntity<?> registerUser(UserDTO userDto) {
        Optional<Role> role = roleRepository.findByName(userDto.getRoleName());
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

    public CustomResponseEntity<?> updateUser(User user){
        Optional<User> user1 = userRepository.findByUsername(user.getUsername());
        if(user1.isPresent()){
            if(user.getName() != null) {
                user1.get().setName(user.getName());
            }
            if(user.getPassword() != null) {
                user1.get().setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(user1.get());
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
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

    public CustomResponseEntity<?> getAllUsers(){
        List<User> users = userRepository.findAll();
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, users);
    }

    @Transactional
    public CustomResponseEntity<?> createUser(User user) {
        Optional<Role> role = roleRepository.findByName(user.getRoleName());
        if(role.isPresent() && user.getEmail() != null) {
            User newUser = new User();
            String username = String.valueOf(System.currentTimeMillis());
            user.setUsername(username);
            String generatedPassword = generatePassword();
            user.setPassword(passwordEncoder.encode(generatedPassword));
            user.setRole(role.get());
            EmailEvent emailEvent = new EmailEvent(this, user.getEmail(), "", "Create Account", username, generatedPassword, EmailEvent.EmailType.CREATE_ACCOUNT);
            eventPublisher.publishEvent(emailEvent);
            userRepository.save(user);
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, jwtService.generateToken(user));
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    public CustomResponseEntity<?> generateOtp(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            int random = new Random().nextInt(9000) + 1000;         //generate OTP
            String otp = Integer.toString(random);
            user.get().setOtp(otp);
            EmailEvent event = new EmailEvent(this, user.get().getEmail(), otp, "OTP CODE", user.get().getUsername(), "", EmailEvent.EmailType.RESET_PASSWORD);
            eventPublisher.publishEvent(event);                           //send OTP
            userRepository.save(user.get());
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    public CustomResponseEntity<?> checkOtp(String otp, String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            if(user.get().getOtp().equals(otp)){
                user.get().setOtp(null);
                userRepository.save(user.get());
                return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, jwtService.generateToken(user.get()));
            }
            user.get().setOtp(null);
            userRepository.save(user.get());
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }

    public String generatePassword() {
        Random random = new Random();
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=";
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
