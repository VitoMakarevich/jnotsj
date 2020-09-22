package com.vito.jnotsj;

import com.vito.jnotsj.auth.entity.Role;
import com.vito.jnotsj.auth.entity.RoleName;
import com.vito.jnotsj.auth.entity.User;
import com.vito.jnotsj.auth.repository.RoleRepository;
import com.vito.jnotsj.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collections;

@Component
@ActiveProfiles(profiles = {"dev"})
@RequiredArgsConstructor
public class ApplicationAuthSeed implements ApplicationRunner {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

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
