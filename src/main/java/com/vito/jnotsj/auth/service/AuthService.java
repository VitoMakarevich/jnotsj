package com.vito.jnotsj.auth.service;

import com.vito.jnotsj.auth.JwtTokenProvider;
import com.vito.jnotsj.auth.entity.Role;
import com.vito.jnotsj.auth.entity.RoleName;
import com.vito.jnotsj.auth.entity.User;
import com.vito.jnotsj.auth.repository.RoleRepository;
import com.vito.jnotsj.auth.repository.UserRepository;
import com.vito.jnotsj.auth.service.error.EmailAlreadyExistsException;
import com.vito.jnotsj.auth.service.error.UsernameAlreadyExistsException;
import com.vito.jnotsj.auth.vo.SignIn.LoginRequest;
import com.vito.jnotsj.auth.vo.SignIn.LoginResponse;
import com.vito.jnotsj.auth.vo.signUp.SignUpRequest;
import com.vito.jnotsj.auth.vo.signUp.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if(this.userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Role role = roleRepository.findByRoleName(RoleName.ADMIN).get();
        user.setRoles(Collections.singleton(role));

        userRepository.save(user);

        return new SignUpResponse();
    }

    @Transactional
    public LoginResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getLogin(),
                                loginRequest.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateAccessToken(authentication);

        User user = userRepository.findOneByUsernameIgnoreCase(loginRequest.getLogin()).get();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setUser(user);

        return loginResponse;
    }
}