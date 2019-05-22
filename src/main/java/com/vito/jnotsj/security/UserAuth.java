package com.vito.jnotsj.security;

import com.vito.jnotsj.entity.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserAuth implements UserDetails {

    private Long id;
    private String username;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities;


    public static UserAuth create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map (role -> new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());

        UserAuth userAuth = new UserAuth();
                userAuth.setId(user.getId());
                userAuth.setUsername(user.getUsername());
                userAuth.setEmail(user.getEmail());
                userAuth.setPassword(user.getPassword());
                userAuth.setAuthorities(authorities);

                return userAuth;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}