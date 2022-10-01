package com.lab2.electronicQueue.configuration;

import com.lab2.electronicQueue.entity.User;
import com.lab2.electronicQueue.entity.UserRole;
import com.lab2.electronicQueue.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if(user == null){
            throw  new UsernameNotFoundException(String.format("User with name '%s' dose not find",username));
        }
        boolean isActive = !user.isActive();
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getUserPassword())
                .disabled(isActive)
                .authorities(mapRolesToAuthority(user.getUserRole()))
                .build();
        return userDetails;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthority(UserRole roles){
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }
}
