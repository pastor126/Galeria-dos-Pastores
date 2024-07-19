package com.pastor126.galeriap.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
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
				"Voçê está recebendo um email de cadastro, para validar clique no link: " + "https://galeria-dos-pastores-production.up.railway.app/auth/verificaCadastro/"
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
	
	 public UsuarioEntity buscarPorLogin(String login) {
	        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByLogin(login);
	        return usuarioOpt.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	    }
	
	public String verificaCadastro(String uuid) {
		VerificadorPendenciaEntity verificaPendencia = verificadorRepository.findByUuid(UUID.fromString(uuid)).get();
		if(verificaPendencia != null) {
			if(verificaPendencia.getDataExpira().compareTo(Instant.now()) >= 0) {
				UsuarioEntity uverificado = verificaPendencia.getUsuario();
				uverificado.setSituacao(SituacaoUsuario.ATIVO);
			usuarioRepository.save(uverificado);	
			return " <a className=\" text-m font-medium text-white hover:bg-red-400 focus-visible:outline-offset-2 focus-visible:outline-red-900   border-2 rounded-md  border-black ml-10 bg-red-500 mt-4 mb-1 pl-4 pr-4 flex items-center w-20\" href='https://pastor-frontend-production.up.railway'>Vá para o Login</a>";
//					https://pastor-frontend-production.up.railway.app/login";
			}
		}else {
			verificadorRepository.delete(verificaPendencia);
			return "Tempo de verificação EXPIRADO!";
		}
		
		return null;
	}
}
