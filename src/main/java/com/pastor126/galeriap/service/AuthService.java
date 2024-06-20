package com.pastor126.galeriap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.AcessDTO;
import com.pastor126.galeriap.dto.AuthenticationDTO;
import com.pastor126.galeriap.security.jwt.JwtUtils;

@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;

	public AcessDTO login(AuthenticationDTO authDTO) {
		
		try {
		//Cria mecanismo de credencial para o spring
		UsernamePasswordAuthenticationToken userAuth = 
				new UsernamePasswordAuthenticationToken(authDTO.getUsername(), authDTO.getPassword());
		
		//Prepara autenticação
		Authentication authentication = authenticationManager.authenticate(userAuth);
		
		//Busca usuário logado
		UserDetailsImpl userAuthenticate = (UserDetailsImpl)authentication.getPrincipal();
		
		String token = jwtUtils.generateTokenFromUserDetailsImpl(userAuthenticate);
		
		AcessDTO acessDTO =new AcessDTO(token);
		return acessDTO;
		
		}catch (BadCredentialsException e) {
			// TODO LOGIN OU SENHA INVALIDO
		}
		return null;
//				new AcessDTO("Acesso negado");
	}
}
