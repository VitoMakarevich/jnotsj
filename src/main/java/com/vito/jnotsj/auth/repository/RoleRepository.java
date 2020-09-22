package com.vito.jnotsj.auth.repository;

import com.vito.jnotsj.auth.entity.Role;
import com.vito.jnotsj.auth.entity.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, RoleName> {
    public Optional<Role> findByRoleName(RoleName roleName);
}