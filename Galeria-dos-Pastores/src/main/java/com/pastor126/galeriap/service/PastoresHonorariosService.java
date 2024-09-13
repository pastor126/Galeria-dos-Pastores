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



@Service
public class PastoresHonorariosService {

	@Autowired
	PastoresHonorariosRepository pastoresHonorariosRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
		
	@Autowired
	PerfilUsuarioService perfilUsuarioService;
	
	   @Autowired
	    private AuthDtoCacheService authDtoCacheService;
	
	   
	public List<PastoresHonorariosDTO> listarTodos() throws IOException{
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
		if(perfil == 1 ||perfil == 3) {
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
	
	
	public PastoresHonorariosDTO buscarPorId(Long id) throws IOException{
		PastoresHonorariosDTO pastores = new PastoresHonorariosDTO();
		if(autentica()) {
		return new PastoresHonorariosDTO(pastoresHonorariosRepository.findById(id).get());
		}else {
			return pastores;
		}
	}
	
	public void inserir(PastoresHonorariosDTO pastores) {
		PastoresHonorariosEntity pastoresHonorariosEntity = new PastoresHonorariosEntity(pastores);
		pastoresHonorariosRepository.save(pastoresHonorariosEntity);
	}
	
	public PastoresHonorariosDTO alterar(Long id, PastoresHonorariosDTO pastores) throws IOException {
		Optional<PastoresHonorariosEntity> pastoresOpt = pastoresHonorariosRepository.findById(id);
		if(autorizacao().equals("adm")) {
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
	
	
	public void excluir(Long id) throws IOException {
		if(autorizacao().equals("adm")) {
		PastoresHonorariosEntity pastores = pastoresHonorariosRepository.findById(id).get();
		pastoresHonorariosRepository.delete(pastores);
		}
	}
	
	
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
