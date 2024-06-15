package com.example.tms.Service;

import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.Role;
import com.example.tms.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public CustomResponseEntity<?> createRole(Role role) {
        roleRepository.save(role);
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
    }

    public CustomResponseEntity<?> getRole(long id) {
        Optional<Role> role = roleRepository.findById(id);
        if(role.isPresent()){
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, role.get());
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }
}
