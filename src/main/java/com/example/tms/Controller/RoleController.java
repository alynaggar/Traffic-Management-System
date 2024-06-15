package com.example.tms.Controller;

import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.Role;
import com.example.tms.Service.RoleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{id}")
    public CustomResponseEntity<?> getRoleById(@PathVariable long id){
        return roleService.getRole(id);
    }

    @PostMapping("/create")
    public CustomResponseEntity<?> createRole(@RequestBody Role role){
        return roleService.createRole(role);
    }

}
