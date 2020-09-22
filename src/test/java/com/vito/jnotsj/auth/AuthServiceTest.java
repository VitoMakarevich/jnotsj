package com.vito.jnotsj.auth;

import com.github.javafaker.Faker;
import com.vito.jnotsj.auth.AuthService;
import com.vito.jnotsj.auth.entity.Role;
import com.vito.jnotsj.auth.entity.RoleName;
import com.vito.jnotsj.auth.repository.RoleRepository;
import com.vito.jnotsj.auth.repository.UserRepository;
import com.vito.jnotsj.auth.JwtTokenProvider;
import com.vito.jnotsj.auth.vo.signUp.SignUpRequest;
import com.vito.jnotsj.auth.vo.signUp.SignUpResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AuthServiceTest {
    AuthService authService;
    @Mock
    UserRepository userRepository;
    @Mock
    RoleRepository roleRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    JwtTokenProvider jwtTokenProvider;
    private Faker faker = new Faker();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        authService = new AuthService(userRepository, roleRepository, passwordEncoder, authenticationManager, jwtTokenProvider);
    }

    @Test
    public void givenUniqueUsernameAndEmail_whenSignUp_thenReturnSuccessResponse() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(faker.internet().emailAddress());
        signUpRequest.setUsername(faker.letterify("??????"));
        signUpRequest.setPassword(faker.letterify("???????"));
        given(userRepository.existsByUsername(signUpRequest.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(signUpRequest.getEmail())).willReturn(false);
        given(roleRepository.findByRoleName(RoleName.ADMIN)).willReturn(Optional.of(new Role()));

        SignUpResponse result = authService.signUp(signUpRequest);

        assertEquals(result.isCreated(), true);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNonUniqueUsername_whenSignUp_thenThrowError() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(faker.internet().emailAddress());
        signUpRequest.setUsername(faker.letterify("??????"));
        signUpRequest.setPassword(faker.letterify("???????"));
        given(userRepository.existsByUsername(signUpRequest.getUsername())).willReturn(true);

        authService.signUp(signUpRequest);

        verify(userRepository.existsByEmail(signUpRequest.getEmail()), never());
        fail("Should throw exception before");
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNonUniqueEmail_whenSignUp_thenThrowError() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(faker.internet().emailAddress());
        signUpRequest.setUsername(faker.letterify("??????"));
        signUpRequest.setPassword(faker.letterify("???????"));
        given(userRepository.existsByUsername(signUpRequest.getUsername())).willReturn(false);
        given(userRepository.existsByEmail(signUpRequest.getEmail())).willReturn(true);

        authService.signUp(signUpRequest);

        fail("Should throw exception before");
    }
}
