package com.javastriker69.blog.security;

import com.javastriker69.blog.model.Role;
import com.javastriker69.blog.model.User;
import com.javastriker69.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;


import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmailOrUsername(username, username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username or email: " + username)
        );

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), mapToGrantedAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapToGrantedAuthority(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
