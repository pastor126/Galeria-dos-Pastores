package com.pastor126.galeriap.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.UsuarioDTO;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.entity.VerificadorPendenciaEntity;
import com.pastor126.galeriap.entity.enums.SituacaoUsuario;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.repository.VerificadorPendenciaRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
			
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificadorPendenciaRepository verificadorRepository;
		
	public List<UsuarioDTO> listarTodos(){
		List<UsuarioEntity> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(UsuarioDTO::new).toList();
	}
	
	public void inserir(UsuarioDTO usuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuarioEntity);
	}
	
	public void inserirNovoUsuario(UsuarioDTO usuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEntity.setSituacao(SituacaoUsuario.PENDENTE);
		usuarioEntity.setId(null);
		usuarioRepository.save(usuarioEntity);
		
		VerificadorPendenciaEntity verificador = new VerificadorPendenciaEntity();
		verificador.setUsuario(usuarioEntity);
		verificador.setUuid(UUID.randomUUID());
		verificador.setDataExpira(Instant.now().plusMillis(900000));
		verificadorRepository.save(verificador);
		
		emailService.enviarEmailTexto(usuario.getEmail(),
				"Novo usuário da Galeria dos Pastores", 
				"Voçê está recebendo um email de cadastro, para validar insira o código: "
				+ verificador.getUuid());
	}
	
	public UsuarioDTO alterar(UsuarioDTO usuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
	}
	
	public void excluir(Long id) {
		UsuarioEntity usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
	}
	
	public UsuarioDTO buscarPorId(Long id) {
		return new UsuarioDTO(usuarioRepository.findById(id).get());
	}
}
