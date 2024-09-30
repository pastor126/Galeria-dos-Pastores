package com.pastor126.galeriap.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.AtualSenDTO;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.security.WebSecurityConfig;
import com.pastor126.galeriap.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AtualSenhaService {
    
	@Autowired
    UsuarioService usuarioService = new UsuarioService();
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private WebSecurityConfig tokenFilter;
	
	@Autowired
	private JwtUtils jwtUtils;

    public void atualiza(AtualSenDTO atualsenha, HttpServletRequest request){
    	
    	String token = tokenFilter.authFilterToken().getToken(request);
	    String login = jwtUtils.getUsernameFromToken(token);
    
       	String senha1 = atualsenha.getSen1();
    	String senha2 = atualsenha.getSen2();
    	System.out.println(senha1 + "  /  " + senha2);
    	
    	Optional<UsuarioEntity> usuario = usuarioRepo.findByLogin(login);
	    UsuarioEntity user = usuario.get();
	    System.out.println("nome: "+user.getNome()+ " login: " + user.getLogin());
    	if(senha1.equals(senha2)) {
    		System.out.println("Chegou aqui");
        	user.setSenha(passwordEncoder.encode(senha2));
        	System.out.println("Depois do set: " + user.getLogin());
        	UsuarioEntity salvo = usuarioRepo.save(user);
        	System.out.println("Salvo " + salvo.getNome());
        }
    	            
    }
}
