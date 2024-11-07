package org.example.proyectotic1grupo1.config;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//
//public class CustomUserDetails implements UserDetails {
//
//    private  List<SimpleGrantedAuthority> authorities;
//    private String username;
//    private String password;
//    private String fullname;
//
//    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
//                             String fullname) {
//        this.username = username;
//        this.password = password;
//        this.fullname = fullname;
//        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//
//    public String getFullname() {
//        return fullname;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Todos los usuarios tendrán el rol "USER"
//        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}