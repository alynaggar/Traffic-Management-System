package com.example.tms.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(RolePrivilege.RolePrivilegeId.class)
public class RolePrivilege {

    @Id
    @ManyToOne
    private Role role;

    @Id
    @ManyToOne
    private Privilege privilege;

    public RolePrivilege() {
    }

    public RolePrivilege(Role role, Privilege privilege) {
        this.role = role;
        this.privilege = privilege;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    @Embeddable
    public static class RolePrivilegeId implements Serializable {

        private long role;

        private long privilege;

        public RolePrivilegeId() {
        }

        public RolePrivilegeId(long role, long privilege) {
            this.role = role;
            this.privilege = privilege;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RolePrivilegeId that = (RolePrivilegeId) o;
            return role == that.role && privilege == that.privilege;
        }

        @Override
        public int hashCode() {
            return Objects.hash(role, privilege);
        }
    }
}