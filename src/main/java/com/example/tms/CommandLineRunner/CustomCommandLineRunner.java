package com.example.tms.CommandLineRunner;

import com.example.tms.Entity.Privilege;
import com.example.tms.Entity.Role;
import com.example.tms.Entity.RolePrivilege;
import com.example.tms.Entity.User;
import com.example.tms.Repository.PrivilegeRepository;
import com.example.tms.Repository.RolePrivilegeRepository;
import com.example.tms.Repository.RoleRepository;
import com.example.tms.Repository.UserRepository;
import com.example.tms.Service.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomCommandLineRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    public CustomCommandLineRunner(UserRepository userRepository, PrivilegeRepository privilegeRepository, RolePrivilegeRepository rolePrivilegeRepository) {
        this.userRepository = userRepository;
        this.privilegeRepository = privilegeRepository;
        this.rolePrivilegeRepository = rolePrivilegeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> user = userRepository.findByUsername("admin");

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
