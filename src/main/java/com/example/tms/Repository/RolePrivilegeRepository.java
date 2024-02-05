package com.example.tms.Repository;

import com.example.tms.Entity.RolePrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, RolePrivilege.RolePrivilegeId> {
}
