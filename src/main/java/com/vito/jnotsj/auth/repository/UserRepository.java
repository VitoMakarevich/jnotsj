package com.vito.jnotsj.auth.repository;

import com.vito.jnotsj.auth.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findOneByUsernameIgnoreCase(String username);
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}