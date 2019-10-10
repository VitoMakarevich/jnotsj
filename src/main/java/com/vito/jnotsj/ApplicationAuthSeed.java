package com.vito.jnotsj;

import com.vito.jnotsj.entity.Role;
import com.vito.jnotsj.entity.RoleName;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.RoleRepository;
import com.vito.jnotsj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Profile("dev")
public class ApplicationAuthSeed implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        String seedUsername = "qqq";
        boolean userExists = userRepository.existsByUsername(seedUsername);
        if (!userExists) {
            Role admin = roleRepository.findByRoleName(RoleName.ADMIN).get();
            User seedUser = new User();
            seedUser.setEmail("seed@mail.com");
            seedUser.setRoles(Collections.singleton(admin));
            seedUser.setPassword(passwordEncoder.encode("qqqqqq"));
            seedUser.setUsername(seedUsername);
            userRepository.save(seedUser);
        }
    }
}
