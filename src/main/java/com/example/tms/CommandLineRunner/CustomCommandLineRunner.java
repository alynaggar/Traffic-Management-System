package com.example.tms.CommandLineRunner;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.tms.Entity.Role;
import com.example.tms.Entity.User;
import com.example.tms.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SocketIOServer server;

    public CustomCommandLineRunner(UserRepository userRepository, PasswordEncoder passwordEncoder, SocketIOServer server) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {

        server.start();                                                             //start websocket server

        Optional<User> user = userRepository.findByUsername("admin");               //add admin to DB
        if(user.isEmpty()){
            Role role = new Role("admin");
            User user1 = new User("admin", passwordEncoder.encode( "admin"), role);
            userRepository.save(user1);
        }
    }
}
