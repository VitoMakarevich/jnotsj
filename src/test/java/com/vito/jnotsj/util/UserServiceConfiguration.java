package com.vito.jnotsj.util;

import com.vito.jnotsj.auth.UserAuth;
import com.vito.jnotsj.auth.entity.Role;
import com.vito.jnotsj.auth.entity.RoleName;
import com.vito.jnotsj.auth.entity.User;
import org.assertj.core.util.Sets;
import org.junit.platform.commons.util.CollectionUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@TestConfiguration
public class UserServiceConfiguration {
    @Bean
    @Primary
    public UserDetailsService getUserDetailsService() {
        User user = new User();
        user.setId((long)1);
        user.setEmail("testuser@jnotsj.com");
        user.setUsername("testuser");
        user.setPassword("simplepassword");
        user.setRoles(Stream.of(RoleName.USER).map((roleName -> {
            Role role = new Role();
            role.setId((long)1);
            role.setRoleName(RoleName.USER);
            return role;
        })).collect(Collectors.toSet()));
        UserAuth userAuth = UserAuth.create(user);
        return new InMemoryUserDetailsManager(Arrays.asList(userAuth));
    }
}
