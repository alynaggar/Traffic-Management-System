package com.example.tms.CommandLineRunner;

import com.corundumstudio.socketio.SocketIOServer;
import com.example.tms.Entity.Privilege;
import com.example.tms.Entity.Role;
import com.example.tms.Entity.RolePrivilege;
import com.example.tms.Entity.User;
import com.example.tms.Repository.PrivilegeRepository;
import com.example.tms.Repository.RolePrivilegeRepository;
import com.example.tms.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;
    private final SocketIOServer server;

    public CustomCommandLineRunner(UserRepository userRepository, PrivilegeRepository privilegeRepository, RolePrivilegeRepository rolePrivilegeRepository, SocketIOServer server) {
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.rolePrivilegeRepository = rolePrivilegeRepository;
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {

        server.start();                                                             //start websocket server

        Optional<User> user = userRepository.findByUsername("admin");               //add admin to DB
        if(user.isEmpty()){
            Role role = new Role("admin");
            Privilege privilege = new Privilege("admin", "have all authorities");
            RolePrivilege rolePrivilege = new RolePrivilege(role, privilege);
            User user1 = new User("admin", "admin", role);
            userRepository.save(user1);
            privilegeRepository.save(privilege);
            rolePrivilegeRepository.save(rolePrivilege);
        }
    }
}
