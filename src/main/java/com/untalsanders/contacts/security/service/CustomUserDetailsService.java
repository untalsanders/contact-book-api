package com.untalsanders.contacts.security.service;

import com.untalsanders.contacts.user.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var domainUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // Mapeamos nuestra entidad de Dominio al objeto User de Spring Security (UserDetails)
        return new User(
                domainUser.getEmail(),
                domainUser.getPassword(),
                authorities
        );
    }
}
