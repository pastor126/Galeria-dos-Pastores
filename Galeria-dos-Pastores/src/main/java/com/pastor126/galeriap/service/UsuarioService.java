package com.pastor126.galeriap.service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.AcessDTO;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.dto.UsuarioDTO;
import com.pastor126.galeriap.entity.AcessEntity;
import com.pastor126.galeriap.entity.PerfilEntity;
import com.pastor126.galeriap.entity.PerfilUsuarioEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.entity.VerificadorPendenciaEntity;
import com.pastor126.galeriap.entity.enums.SituacaoUsuario;
import com.pastor126.galeriap.repository.AcessRepository;
import com.pastor126.galeriap.repository.PerfilRepository;
import com.pastor126.galeriap.repository.PerfilUsuarioRepository;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.repository.VerificadorPendenciaRepository;
import com.pastor126.galeriap.security.WebSecurityConfig;
import com.pastor126.galeriap.security.jwt.JwtUtils;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilUsuarioRepository perfilUsuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	public AcessService acessService;
	
	@Autowired
	public AcessRepository acessRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
			
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private WebSecurityConfig tokenFilter;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private VerificadorPendenciaRepository verificadorRepository;
	
	 public String autorizacao(HttpServletRequest request) throws IOException {
	        // Recupera o token da requisição
	        String token = tokenFilter.authFilterToken().getToken(request);
	        
	        // Extrai o login (username) do token
	        String login = jwtUtils.getUsernameFromToken(token);

	        if (login == null) {
	            throw new IOException("authDto não encontrado");
	        }

	        System.out.println("Login é: " + login);
	        
	        // Busca o usuário no banco a partir do login
	        Optional<UsuarioEntity> usuario = usuarioRepository.findByLogin(login);

	        if (usuario.isEmpty()) {
	            throw new IOException("Usuário não encontrado");
	        }

	        Long idU = usuario.get().getId();
	        System.out.println("idU é: " + idU);

	        // Busca todos os perfis de usuário e verifica se o usuário tem o perfil "administrador"
	        String perfil = null;
	        List<PerfilUsuarioDTO> lista = perfilUsuarioService.listarTodos();
	        
	        for (PerfilUsuarioDTO usuarioP : lista) {
	            if (usuarioP.getUsuario().getId().equals(idU)) {
	                perfil = usuarioP.getPerfil().getDescricao();
	                System.out.println("perfil é: " + perfil);
	                break;
	            }
	        }

	        if ("administrador".equals(perfil)) {
	            System.out.println("perfil autorizado - adm");
	            return "adm";
	        }

	        System.out.println("perfil não autorizado");
	        return "Sem acesso";
	    }


	
		
	public List<UsuarioDTO> listarTodos(HttpServletRequest request) throws IOException{
		if(autorizacao(request).equals("adm")) {
		List<UsuarioEntity> usuarios = usuarioRepository.findAll();

		return usuarios.stream()
	            .map(UsuarioDTO::new)
	            .sorted(Comparator.comparing(UsuarioDTO::getNome))
	            .collect(Collectors.toList());
	}
		List<UsuarioEntity> usuarios = new ArrayList<>();
		return usuarios.stream().map(UsuarioDTO::new).toList();
	}
	

	public void inserirNovoUsuario(UsuarioDTO usuario) {
		UsuarioEntity usuarioEntity = new UsuarioEntity(usuario);
		AcessDTO acessDTO = new AcessDTO();
		usuarioEntity.setSenha(passwordEncoder.encode(usuario.getSenha()));
		usuarioEntity.setSituacao(SituacaoUsuario.PENDENTE);
		usuarioEntity.setId(null);
		usuarioRepository.save(usuarioEntity);
		acessDTO.setUsername(usuarioEntity.getLogin());
		acessService.inserir(acessDTO);
		
		PerfilUsuarioEntity perfilUsu = new PerfilUsuarioEntity();
		Long idp = 2L;
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
	
	public UsuarioDTO alterar(Long id, UsuarioDTO usuario, HttpServletRequest request) throws IOException {
		 Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(id);
		if(autorizacao(request).equals("adm")) {
			UsuarioEntity usuarioedit = optionalUsuario.get();
	        usuarioedit.setNome(usuario.getNome());
	        usuarioedit.setLogin(usuario.getLogin());
	        usuarioedit.setEmail(usuario.getEmail());
	        usuarioedit.setSituacao(usuario.getSituacao());
	        
	        AcessEntity acess = new AcessEntity(acessService.findByUsername(usuarioedit.getLogin()));
	        acess.setUsername(usuario.getLogin());
	        acessRepo.save(acess);

	        UsuarioEntity usuarioAtualizado = usuarioRepository.save(usuarioedit);

	        // Converte a entidade para DTO e retorna
	        return new UsuarioDTO(usuarioAtualizado);
	}
	UsuarioDTO u = new UsuarioDTO();
	return u;
}
	
	
	
	public void excluir(Long id, HttpServletRequest request) throws IOException {
		if(autorizacao(request).equals("adm")) {
		UsuarioEntity usuario = usuarioRepository.findById(id).get();
		Long idPerfil = perfilUsuarioRepository.findByUsuario(usuario).get().getId();
		String username = usuario.getLogin();
		perfilUsuarioService.excluir(idPerfil);
		AcessEntity acess = new AcessEntity(acessService.findByUsername(username));
		acessService.excluir(acess.getId());
		usuarioRepository.deleteById(id);
		}
	}
	
	public UsuarioDTO buscarPorId(Long id, HttpServletRequest request) throws IOException {
		if(autorizacao(request).equals("adm")) {
		return new UsuarioDTO(usuarioRepository.findById(id).get());
	}
		UsuarioDTO u = new UsuarioDTO();
		return u;
	}
	
	 public UsuarioEntity buscarPorLogin(String login, HttpServletRequest request) throws IOException, RuntimeException {
			if(autorizacao(request).equals("adm")) {
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
			verificadorRepository.delete(verificaPendencia);
			return "<a className=\" text-5xl font-medium text-white hover:bg-red-400 focus-visible:outline-offset-2 focus-visible:outline-red-900   border-2 rounded-md  border-black ml-10 bg-red-500 mt-20 ml-20 pl-4 pr-4 flex items-center w-20\" href='https://pastor-frontend-production.up.railway.app'>Vá para o Login</a>";
			}
		}else {
			verificadorRepository.delete(verificaPendencia);
			return "<a className=\" text-2xl font-medium text-white hover:bg-red-400 focus-visible:outline-offset-2 focus-visible:outline-red-900   border-2 rounded-md  border-black ml-10 bg-red-500 mt-20 ml-20 pl-4 pr-4 flex items-center w-20\" href='https://pastor-frontend-production.up.railway.app'>Tempo de verificação expirado! Faça novo cadastro e verifque seu email em até 15 minutos para validar.</a>";
		}
		
		return null;
	}
}
