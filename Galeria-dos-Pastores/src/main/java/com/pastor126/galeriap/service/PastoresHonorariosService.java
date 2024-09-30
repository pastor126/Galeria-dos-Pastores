package com.pastor126.galeriap.service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.PastoresHonorariosDTO;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.PastoresHonorariosEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.PastoresHonorariosRepository;
import com.pastor126.galeriap.repository.UsuarioRepository;
import com.pastor126.galeriap.security.WebSecurityConfig;
import com.pastor126.galeriap.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class PastoresHonorariosService {

	@Autowired
	private PastoresHonorariosRepository pastoresHonorariosRepository;
		
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
    private JwtUtils jwtUtils;
	
	@Autowired
	private WebSecurityConfig tokenFilter;
		   
	public List<PastoresHonorariosDTO> listarTodos(HttpServletRequest request) throws IOException{
		String perfil= autorizacao(request);
		
		if("administrador".equals(perfil) || "parasar".equals(perfil)) {
			 List<PastoresHonorariosEntity> pastores = pastoresHonorariosRepository.findAll();
			return pastores.stream()
					.map(PastoresHonorariosDTO :: new)
					.sorted(Comparator.comparing(p -> Integer.parseInt(p.getNumero())))
					.collect(Collectors.toList());
	}else {
	
			System.out.println("parou aqui");
			List<PastoresHonorariosEntity> pastores = pastoresHonorariosRepository.findAll();
			for(PastoresHonorariosEntity pastor : pastores) {
				pastor.setNome(null);
			}
			return pastores.stream()
					.map(PastoresHonorariosDTO :: new)
					.sorted(Comparator.comparing(p -> Integer.parseInt(p.getNumero())))
		            .collect(Collectors.toList());
			}	
			
	}
	
	
	public PastoresHonorariosDTO buscarPorId(Long id, HttpServletRequest request) throws IOException{
		PastoresHonorariosDTO pastores = new PastoresHonorariosDTO();
		if(autorizacao(request).equalsIgnoreCase("administrador")) {
		return new PastoresHonorariosDTO(pastoresHonorariosRepository.findById(id).get());
		}else {
			return pastores;
		}
	}
	
	public void inserir(PastoresHonorariosDTO pastores) {
		PastoresHonorariosEntity pastoresHonorariosEntity = new PastoresHonorariosEntity(pastores);
		pastoresHonorariosRepository.save(pastoresHonorariosEntity);
	}
	
	public PastoresHonorariosDTO alterar(Long id, PastoresHonorariosDTO pastores, HttpServletRequest request) throws IOException {
		Optional<PastoresHonorariosEntity> pastoresOpt = pastoresHonorariosRepository.findById(id);
		if(autorizacao(request).equals("administrador")) {
			PastoresHonorariosEntity pastoredit = pastoresOpt.get();
	        pastoredit.setNumero(pastores.getNumero());
	        pastoredit.setNome(pastores.getNome());
	        pastoredit.setIniciais(pastores.getIniciais());
	        PastoresHonorariosEntity pastoratual = pastoresHonorariosRepository.save(pastoredit);
	      
	        // Converte a entidade para DTO e retorna
	        return new PastoresHonorariosDTO(pastoratual);
	}
	PastoresHonorariosDTO u = new PastoresHonorariosDTO();
	return u;
}
		
	public void excluir(Long id, HttpServletRequest request) throws IOException {
		if(autorizacao(request).equals("administrador")) {
		PastoresHonorariosEntity pastores = pastoresHonorariosRepository.findById(id).get();
		pastoresHonorariosRepository.delete(pastores);
		}
	}
	
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
                System.out.println("perfil é: " + perfil);
                break;
            }
        }

          return perfil;
        }

	}
