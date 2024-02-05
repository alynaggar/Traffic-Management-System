package com.example.tms.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> user;

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<RolePrivilege> rolePrivileges;

    public Role() {
    }

    public Role(long id, String name, List<User> user, List<RolePrivilege> rolePrivileges) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.rolePrivileges = rolePrivileges;
    }

    public Role(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public List<RolePrivilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(List<RolePrivilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }
}