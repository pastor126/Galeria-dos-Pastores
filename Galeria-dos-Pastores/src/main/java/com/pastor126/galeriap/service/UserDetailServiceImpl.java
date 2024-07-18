package com.pastor126.galeriap.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.UsuarioRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Tentando carregar usuário com login: " + username);
        UsuarioEntity usuario = usuarioRepository.findByLogin(username)
            .orElseThrow(() -> {
                logger.error("Usuário não encontrado com login: " + username);
                return new UsernameNotFoundException("Usuário não encontrado com login: " + username);
            });
        
        logger.info("Usuário encontrado: " + usuario.getLogin() + ", " + usuario.getNome());
        return UserDetailsImpl.build(usuario);
    }
}
