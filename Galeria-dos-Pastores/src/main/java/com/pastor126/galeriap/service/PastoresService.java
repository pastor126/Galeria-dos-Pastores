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



@Service
public class PastoresService {

	@Autowired
	private PastoresRepository pastoresRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	public AuthDtoCacheService authDtoCacheService;
	
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
		
	
	
	
	public List<PastoresDTO> listarTodos() throws IOException{
		Long perfil=null;
		String login = authDtoCacheService.get("authDto");
		 if (login == null) {
	            throw new IOException("authDto não encontrado");
	        }
		System.out.println("Login é: "+login);
		UsuarioEntity usuario = usuarioService.buscarPorLogin(login);
		Long idU = usuario.getId();
		System.out.println("idU é: "+ idU);
		List<PerfilUsuarioDTO> lista = perfilUsuarioService.listarTodos();
		for(PerfilUsuarioDTO usuarioP : lista) {
			if(usuarioP.getUsuario().getId().equals(idU)) {
				perfil = usuarioP.getPerfil().getId();
				System.out.println("perfil é: "+ perfil);
			break;
			}			
		}
		if(perfil == 1 || perfil == 3) {
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
	
	
	
	
	public PastoresDTO buscarPorId(Long id) throws IOException{
		PastoresDTO pastores = new PastoresDTO();
		if(autentica()) {
			return new PastoresDTO(pastoresRepository.findById(id).get());
		}else {
			return pastores;
		}	
		
	}
	
	
	public void inserir(PastoresDTO pastores) {
		PastoresEntity pastoresEntity = new PastoresEntity(pastores);
		pastoresRepository.save(pastoresEntity);
	}
	
	public PastoresDTO alterar(Long id, PastoresDTO pastores) throws IOException {
		Optional<PastoresEntity> pastoresOpt = pastoresRepository.findById(id);
		if(autorizacao().equals("adm")) {
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
		
	
	public void excluir(Long id) throws IOException {
		if(autorizacao().equals("adm")) {
		PastoresEntity pastores = pastoresRepository.findById(id).get();
		pastoresRepository.delete(pastores);
		}
		}
	
	public boolean autentica() throws IOException {
		String perfil=null;
		String login = authDtoCacheService.get("authDto");
		 if (login == null) {
	            throw new IOException("authDto não encontrado");
	        }
		UsuarioEntity usuario = usuarioService.buscarPorLogin(login);
		Long idU = usuario.getId();
		System.out.println("idU é: "+ idU);
		List<PerfilUsuarioDTO> lista = perfilUsuarioService.listarTodos();
		for(PerfilUsuarioDTO usuarioP : lista) {
			if(usuarioP.getUsuario().getId().equals(idU)) {
				perfil = usuarioP.getPerfil().getDescricao();
			break;
			}			
		}
		if("administrador".equals(perfil)) {
			 boolean autentica = true;
			return autentica;
	}else {
			boolean autentica = false;
			return autentica;
			}	
	
	}
	
}
