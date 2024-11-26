package com.pastor126.galeriap.service;

import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.AtualSenDTO;
import com.pastor126.galeriap.dto.PerfilDTO;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.PerfilUsuarioRepository;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.security.WebSecurityConfig;
import com.pastor126.galeriap.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AtualSenhaService {

	@Autowired
	UsuarioService usuarioService = new UsuarioService();
	
	@Autowired
	PerfilUsuarioService perfilUsuarioService = new PerfilUsuarioService();

	@Autowired
	UsuarioRepository usuarioRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WebSecurityConfig tokenFilter;

	@Autowired
	private JwtUtils jwtUtils;

	
	@Autowired
	PerfilUsuarioRepository perfilUsuarioRepository;

	public void atualiza(AtualSenDTO atualsenha, HttpServletRequest request) {

		String token = tokenFilter.authFilterToken().getToken(request);
		String login = jwtUtils.getUsernameFromToken(token);

		String senha1 = atualsenha.getSen1();
		String senha2 = atualsenha.getSen2();

		Optional<UsuarioEntity> usuario = usuarioRepo.findByLogin(login);
		UsuarioEntity user = usuario.get();
		System.out.println("nome: " + user.getNome() + " login: " + user.getLogin());
		if (senha1.equals(senha2)) {
			user.setSenha(passwordEncoder.encode(senha2));
			UsuarioEntity salvo = usuarioRepo.save(user);
			System.out.println("Salvo " + salvo.getNome());
		}
	}
	
	
	public void excluiconta(HttpServletRequest request) throws IOException {
		String token = tokenFilter.authFilterToken().getToken(request);
		String login = jwtUtils.getUsernameFromToken(token);
		PerfilDTO perfilnovo = new PerfilDTO();
		perfilnovo.setId(1L);

		Optional<UsuarioEntity> usuario = usuarioRepo.findByLogin(login);
		UsuarioEntity user = usuario.get();
		Long idUsuario = user.getId();
	
		
		PerfilUsuarioDTO  perfilUsuarioNovo = perfilUsuarioService.buscarPorUsuario(user);
		perfilUsuarioNovo.setPerfil(perfilnovo);
		perfilUsuarioService.alterar(perfilUsuarioNovo);
//		perfilUsuarioService.excluir(idUsuario);
//		usuarioRepo.deleteById(idUsuario);
		usuarioService.excluir(idUsuario, request);

	}
}
