package com.example.tms.Service;

import com.example.tms.Entity.Privilege;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Repository.PrivilegeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;

    public PrivilegeService(PrivilegeRepository privilegeRepository) {
        this.privilegeRepository = privilegeRepository;
    }


    public CustomResponseEntity<?> createPrivilege(Privilege privilege) {
        privilegeRepository.save(privilege);
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
    }

    public CustomResponseEntity<?> getPrivilege(long id){
        Optional<Privilege> privilege = privilegeRepository.findById(id);
        if(privilege.isPresent()){
            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, privilege.get());
        }
        return new CustomResponseEntity<>(CustomResponseCode.NOT_FOUND);
    }
}
