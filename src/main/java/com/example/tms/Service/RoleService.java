package com.example.tms.Service;

import com.example.tms.DTO.RolePrivilegeDTO;
import com.example.tms.Entity.Privilege;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.Role;
import com.example.tms.Entity.RolePrivilege;
import com.example.tms.Repository.PrivilegeRepository;
import com.example.tms.Repository.RolePrivilegeRepository;
import com.example.tms.Repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final RolePrivilegeRepository rolePrivilegeRepository;

    public RoleService(RoleRepository roleRepository, PrivilegeRepository privilegeRepository, RolePrivilegeRepository rolePrivilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.rolePrivilegeRepository = rolePrivilegeRepository;
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

    public CustomResponseEntity<?> linkPrivilege(RolePrivilegeDTO rolePrivilegeDto) {
        Optional<Role> role = roleRepository.findById(rolePrivilegeDto.getRoleId());
        if(role.isPresent()) {
            Optional<Privilege> privilege = privilegeRepository.findById(rolePrivilegeDto.getPrivilegeId());
            if(privilege.isPresent()){
                RolePrivilege rolePrivilege = new RolePrivilege(role.get(), privilege.get());
                rolePrivilegeRepository.save(rolePrivilege);
                return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
            }
            return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }
}
