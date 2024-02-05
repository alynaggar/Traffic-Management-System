package com.example.tms.DTO;

public class RolePrivilegeDTO {

    private long roleId;
    private long privilegeId;

    public RolePrivilegeDTO(long roleId, long privilegeId) {
        this.roleId = roleId;
        this.privilegeId = privilegeId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(long privilegeId) {
        this.privilegeId = privilegeId;
    }
}
