package com.vito.jnotsj.service;

import com.vito.jnotsj.entity.Role;
import com.vito.jnotsj.entity.RoleName;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.repository.RoleRepository;
import com.vito.jnotsj.repository.UserRepository;
import com.vito.jnotsj.security.JwtTokenProvider;
import com.vito.jnotsj.vo.SignIn.LoginRequest;
import com.vito.jnotsj.vo.SignIn.LoginResponse;
import com.vito.jnotsj.vo.signUp.SignUpRequest;
import com.vito.jnotsj.vo.signUp.SignUpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if(this.userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "${username.duplicate}"
            );
        }
        if(userRepository.existsByEmail(signUpRequest.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "${email.duplicate}"
            );
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Role role = roleRepository.findByRoleName(RoleName.ADMIN).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND) );
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
        String token = jwtTokenProvider.generateToken(authentication);

        User user = userRepository.findOneByUsernameIgnoreCase(loginRequest.getLogin()).get();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(token);
        loginResponse.setUser(user);

        return loginResponse;
    }
}