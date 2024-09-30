package com.pastor126.galeriap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.AcessDTO;
import com.pastor126.galeriap.dto.AuthenticationDTO;
import com.pastor126.galeriap.entity.AcessEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.AcessRepository;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtUtils jwtUtils;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AcessRepository acessRepository;
    
    public AcessDTO login(AuthenticationDTO authDTO) {
        try {
        	// Valida a situação do usuário
            UsuarioEntity usuario = usuarioRepository.findByLogin(authDTO.getUsername()).get();
            if (!"A".equals(usuario.getSituacao().getCodigo())) {
                throw new BadCredentialsException("Usuário com situação inválida");
            }
        	
            // Cria mecanismo de credencial para o spring
            UsernamePasswordAuthenticationToken userAuth = 
                    new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
            
            // Prepara autenticação
            Authentication authentication = authenticationManager.authenticate(userAuth);
            
            // Busca usuário logado
            UserDetailsImpl userAuthenticate = (UserDetailsImpl) authentication.getPrincipal();
            
            String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
            
            AcessEntity acessEntity = acessRepository.findByUsername(authDTO.getUsername()).get();
            acessEntity.setToken(token);
            acessRepository.save(acessEntity);
            AcessDTO acessDTO = new AcessDTO(acessEntity);
            return acessDTO;
            
        } catch (BadCredentialsException e) {
            // Log da exceção para depuração
            logger.error("Erro de credenciais: Usuário inexistente ou senha inválida");
            throw e; // Re-lançar a exceção para que seja tratada pelo Spring Security
        } catch (AuthenticationException e) {
            // Outras exceções de autenticação
            logger.error("Erro de autenticação: " + e.getMessage());
            throw e; // Re-lançar a exceção para que seja tratada pelo Spring Security
        }
    }
}
