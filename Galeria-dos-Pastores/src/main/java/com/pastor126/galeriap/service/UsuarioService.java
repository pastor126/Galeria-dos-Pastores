package com.pastor126.galeriap.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.dto.UsuarioDTO;
import com.pastor126.galeriap.entity.PerfilEntity;
import com.pastor126.galeriap.entity.PerfilUsuarioEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.entity.VerificadorPendenciaEntity;
import com.pastor126.galeriap.entity.enums.SituacaoUsuario;
import com.pastor126.galeriap.repository.PerfilRepository;
import com.pastor126.galeriap.repository.PerfilUsuarioRepository;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.repository.VerificadorPendenciaRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilUsuarioRepository perfilUsuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	public AuthDtoCacheService authDtoCacheService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
			
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificadorPendenciaRepository verificadorRepository;
	
	private String autorizacao() throws IOException {
		String perfil=null;
		String login = authDtoCacheService.get("authDto");
		 if (login == null) {
	            throw new IOException("authDto não encontrado");
	        }
		System.out.println("Login é: "+login);
		Optional<UsuarioEntity> usuario = usuarioRepository.findByLogin(login);
		Long idU = usuario.get().getId();
		System.out.println("idU é: "+ idU);
		List<PerfilUsuarioDTO> lista = perfilUsuarioService.listarTodos();
		for(PerfilUsuarioDTO usuarioP : lista) {
			if(usuarioP.getUsuario().getId().equals(idU)) {
				perfil = usuarioP.getPerfil().getDescricao();
				System.out.println("perfil é: "+ perfil);
			break;
			}			
		}
		if("administrador".equals(perfil)) {
			System.out.print("perfil autorizado - adm");
			return "adm";
		}
		System.out.print("perfil não autorizado");
			return "Sem acesso";
			}
		
	public List<UsuarioDTO> listarTodos() throws IOException{
		if(autorizacao().equals("adm")) {
		List<UsuarioEntity> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(UsuarioDTO::new).toList();
	}
		List<UsuarioEntity> usuarios = new ArrayList<>();
		return usuarios.stream().map(UsuarioDTO::new).toList();
	}
	
		
	public void inserir(UsuarioDTO usuario) throws IOException {
		if(autorizacao().equals("adm")) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioRepository.save(usuarioEntity);
	}
	}
	
	public void inserirNovoUsuario(UsuarioDTO usuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEntity.setSituacao(SituacaoUsuario.PENDENTE);
		usuarioEntity.setId(null);
		usuarioRepository.save(usuarioEntity);
		
		PerfilUsuarioEntity perfilUsu = new PerfilUsuarioEntity();
		Long idp = (long) 2;
		Optional<PerfilEntity> perfilOp = perfilRepository.findById(idp);
		PerfilEntity perfil = perfilOp.get();
		
		perfilUsu.setPerfil(perfil);
		perfilUsu.setUsuario(usuarioEntity);
		perfilUsuarioRepository.save(perfilUsu);
		
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
	
	public UsuarioDTO alterar(UsuarioDTO usuario) throws IOException {
		if(autorizacao().equals("adm")) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		return new UsuarioDTO(usuarioRepository.save(usuarioEntity));
	}
	UsuarioDTO u = new UsuarioDTO();
	return u;
}
	
	public void excluir(Long id) throws IOException {
		if(autorizacao().equals("adm")) {
		UsuarioEntity usuario = usuarioRepository.findById(id).get();
		usuarioRepository.delete(usuario);
		}
	}
	
	public UsuarioDTO buscarPorId(Long id) throws IOException {
		if(autorizacao().equals("adm")) {
		return new UsuarioDTO(usuarioRepository.findById(id).get());
	}
		UsuarioDTO u = new UsuarioDTO();
		return u;
	}
	
	 public UsuarioEntity buscarPorLogin(String login) throws IOException, RuntimeException {
			if(autorizacao().equals("adm")) {
	        Optional<UsuarioEntity> usuarioOpt = usuarioRepository.findByLogin(login);
	        return usuarioOpt.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
	    }
	 UsuarioEntity ue = new UsuarioEntity();
		return ue;
	}
	
	public String verificaCadastro(String uuid) {
		VerificadorPendenciaEntity verificaPendencia = verificadorRepository.findByUuid(UUID.fromString(uuid)).get();
		if(verificaPendencia != null) {
			if(verificaPendencia.getDataExpira().compareTo(Instant.now()) >= 0) {
				UsuarioEntity uverificado = verificaPendencia.getUsuario();
				uverificado.setSituacao(SituacaoUsuario.ATIVO);
			usuarioRepository.save(uverificado);	
			return "<a className=\" text-5xl font-medium text-white hover:bg-red-400 focus-visible:outline-offset-2 focus-visible:outline-red-900   border-2 rounded-md  border-black ml-10 bg-red-500 mt-20 ml-20 pl-4 pr-4 flex items-center w-20\" href='https://pastor-frontend-production.up.railway.app'>Vá para o Login</a>";
//					https://pastor-frontend-production.up.railway.app/login";
			}
		}else {
			verificadorRepository.delete(verificaPendencia);
			return "<a className=\" text-2xl font-medium text-white hover:bg-red-400 focus-visible:outline-offset-2 focus-visible:outline-red-900   border-2 rounded-md  border-black ml-10 bg-red-500 mt-20 ml-20 pl-4 pr-4 flex items-center w-20\" href='https://pastor-frontend-production.up.railway.app'>Tempo de verificação expirado! Faça novo cadastro e verifque seu email em até 15 minutos para validar.</a>";
		}
		
		return null;
	}
}
