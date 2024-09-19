package com.pastor126.galeriap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.entity.UsuarioEntity;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository userRepository;
    
    @Autowired
    private PerfilUsuarioService perfilUsuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return UserDetailsImpl.build(user, perfilUsuarioService);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
    	UsuarioEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

        return UserDetailsImpl.build(user, perfilUsuarioService);
    }
}