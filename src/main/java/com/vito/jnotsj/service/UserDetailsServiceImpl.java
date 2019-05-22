package com.vito.jnotsj.service;

import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.UserRepository;
import com.vito.jnotsj.security.UserAuth;
import lombok.EqualsAndHashCode;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userRepository.findOneByUsernameIgnoreCase(username).orElseThrow (
                () -> new UsernameNotFoundException(username)
        );

        return UserAuth.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow (
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserAuth.create(user);
    }
}