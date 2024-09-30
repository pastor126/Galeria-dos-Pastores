package com.pastor126.galeriap.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.PastoresDTO;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.PastoresEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.PastoresRepository;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.security.WebSecurityConfig;
import com.pastor126.galeriap.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PastoresService {

	@Autowired
	private PastoresRepository pastoresRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	private WebSecurityConfig tokenFilter;
	
	@Autowired
    private JwtUtils jwtUtils;
	
	 public String autorizacao(HttpServletRequest request) throws IOException {
	        // Recupera o token da requisição
	        String token = tokenFilter.authFilterToken().getToken(request);
	        
	        // Extrai o login (username) do token
	        String login = jwtUtils.getUsernameFromToken(token);

	        if (login == null) {
	            throw new IOException("authDto não encontrado");
	        }
	        
	        // Busca o usuário no banco a partir do login
	        Optional<UsuarioEntity> usuario = usuarioRepository.findByLogin(login);

	        if (usuario.isEmpty()) {
	            throw new IOException("Usuário não encontrado");
	        }
	        Long idU = usuario.get().getId();

	        // Busca todos os perfis de usuário e verifica se o usuário tem o perfil "administrador"
	        String perfil = null;
	        List<PerfilUsuarioDTO> lista = perfilUsuarioService.listarTodos();
	        
	        for (PerfilUsuarioDTO usuarioP : lista) {
	            if (usuarioP.getUsuario().getId().equals(idU)) {
	                perfil = usuarioP.getPerfil().getDescricao();
	                break;
	            }
	        }

	          return perfil;
	        }

	
	public List<PastoresDTO> listarTodos(HttpServletRequest request) throws IOException{
		String perfil= autorizacao(request);

		if("administrador".equals(perfil) || "parasar".equals(perfil)) {
			 List<PastoresEntity> pastores = pastoresRepository.findAll();
			 return pastores.stream()
			            .map(PastoresDTO::new)
			            .sorted(Comparator.comparing(p -> Integer.parseInt(p.getNumero())))
			            .collect(Collectors.toList());
	}else {
			System.out.println("parou aqui");
			List<PastoresEntity> pastores = pastoresRepository.findAll();
			for(PastoresEntity pastor : pastores) {
				pastor.setNome(null);
			}
			return pastores.stream()
		            .map(PastoresDTO::new)
		            .sorted(Comparator.comparing(p -> Integer.parseInt(p.getNumero())))
		            .collect(Collectors.toList());
			}	
	}
		
	
	public PastoresDTO buscarPorId(Long id,  HttpServletRequest request) throws IOException{
		PastoresDTO pastores = new PastoresDTO();
		if(autorizacao(request).equals("administrador")) {
			return new PastoresDTO(pastoresRepository.findById(id).get());
		}else {
			return pastores;
		}			
	}
	
	
	public void inserir(PastoresDTO pastores) {
		PastoresEntity pastoresEntity = new PastoresEntity(pastores);
		pastoresRepository.save(pastoresEntity);
	}
	
	public PastoresDTO alterar(Long id, PastoresDTO pastores, HttpServletRequest request) throws IOException {
		Optional<PastoresEntity> pastoresOpt = pastoresRepository.findById(id);
		if(autorizacao(request).equals("administrador")) {
			PastoresEntity pastoredit = pastoresOpt.get();
	        pastoredit.setNumero(pastores.getNumero());
	        pastoredit.setNome(pastores.getNome());
	        pastoredit.setIniciais(pastores.getIniciais());
	        PastoresEntity pastoratual = pastoresRepository.save(pastoredit);
	      
	        // Converte a entidade para DTO e retorna
	        return new PastoresDTO(pastoratual);
	}
	PastoresDTO u = new PastoresDTO();
	return u;
}
		
	
	public void excluir(Long id, HttpServletRequest request) throws IOException {
		if(autorizacao(request).equals("administrador")) {
		PastoresEntity pastores = pastoresRepository.findById(id).get();
		pastoresRepository.delete(pastores);
		}
		}

}
