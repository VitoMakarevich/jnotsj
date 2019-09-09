package com.vito.jnotsj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.vito.jnotsj.controller.AuthController;
import com.vito.jnotsj.entity.User;
import com.vito.jnotsj.security.JwtAuthenticationEntryPoint;
import com.vito.jnotsj.security.JwtTokenProvider;
import com.vito.jnotsj.service.AuthService;
import com.vito.jnotsj.service.UserDetailsServiceImpl;
import com.vito.jnotsj.vo.SignIn.LoginRequest;
import com.vito.jnotsj.vo.SignIn.LoginResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import javax.servlet.ServletContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    private Faker faker = new Faker();

    private LoginRequest generateLoginRequest(boolean valid) {
        LoginRequest request = new LoginRequest();
        request.setLogin(faker.letterify("??##"));
        if (valid) {
            request.setPassword(faker.witcher().character());
        }

        return request;
    }

    @Test
    public void contextLoad() throws Exception {
        LoginRequest loginRequest = generateLoginRequest(true);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(new User());
        loginResponse.setAccessToken(faker.bothify(""));

        this.mockMvc.perform(
                post("/auth/signin")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJson(loginRequest))
        )
                .andDo(print())
                .andExpect(content().string(asJson(loginResponse)))
                .andExpect(status().isOk());
    }

    private static String asJson(Object object) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    private Object entityFromJson(String json, Class type) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, type);
    }
}
