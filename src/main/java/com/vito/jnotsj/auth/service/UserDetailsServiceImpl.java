package com.vito.jnotsj.auth.service;

import com.vito.jnotsj.auth.UserAuth;
import com.vito.jnotsj.auth.entity.User;
import com.vito.jnotsj.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        val user = userRepository.findOneByUsernameIgnoreCase(username).orElseThrow (
                () -> new UsernameNotFoundException(username)
        );

        return UserAuth.create(user);
    }
}