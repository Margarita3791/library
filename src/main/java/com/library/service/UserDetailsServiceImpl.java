package com.library.service;

import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден: " + email));

        // Предполагаем, что роль пользователя - это строка. Если это Enum, необходимо будет её конвертировать.
        String role = user.getRole();

        // Создаем SimpleGrantedAuthority для ролей
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority(role))) // Устанавливаем роль как authorities
                .accountLocked(!user.isAccountNonLocked()) // Дополнительная информация о блокировке аккаунта
                .credentialsExpired(!user.isCredentialsNonExpired()) // Дополнительная информация о сроке действия учетных данных
                .disabled(!user.isEnabled()) // Учитываем, если аккаунт отключен
                .build();
    }
}
