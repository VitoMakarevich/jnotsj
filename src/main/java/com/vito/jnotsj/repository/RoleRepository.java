package com.vito.jnotsj.repository;

import com.vito.jnotsj.entity.Role;
import com.vito.jnotsj.entity.RoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, RoleName> {
    public Optional<Role> findByRoleName(RoleName roleName);
}