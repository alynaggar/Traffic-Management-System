package com.example.tms.Controller;

import com.example.tms.Entity.Privilege;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Service.PrivilegeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    public PrivilegeController(PrivilegeService privilegeService) {
        this.privilegeService = privilegeService;
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<?> getPrivilegeById(@PathVariable long id){
        return privilegeService.getPrivilege(id);
    }

    @PostMapping("/create")
    public CustomResponseEntity<?> createPrivilege(@RequestBody Privilege privilege){
        return privilegeService.createPrivilege(privilege);
    }
}
