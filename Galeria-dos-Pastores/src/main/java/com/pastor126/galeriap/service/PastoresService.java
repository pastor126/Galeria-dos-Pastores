package com.pastor126.galeriap.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.PastoresDTO;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.PastoresEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.PastoresRepository;



@Service
public class PastoresService {

	@Autowired
	private PastoresRepository pastoresRepository;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PerfilUsuarioService perfilUsuarioService;
	
	@Autowired
	public AuthDtoCacheService authDtoCacheService;
	
	
	
	public List<PastoresDTO> listarTodos() throws IOException{
		String perfil=null;
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
				perfil = usuarioP.getPerfil().getDescricao();
				System.out.println("perfil é: "+ perfil);
			break;
			}			
		}
		if("administrador".equals(perfil) || "parasar".equals(perfil)) {
			 List<PastoresEntity> pastores = pastoresRepository.findAll();
			return pastores.stream().map(PastoresDTO :: new).toList();
	}else {
	
			System.out.println("parou aqui");
			List<PastoresEntity> pastores = pastoresRepository.findAll();
			for(PastoresEntity pastor : pastores) {
				pastor.setNome(null);
			}
			return pastores.stream().map(PastoresDTO :: new).toList();
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
	
	public PastoresDTO alterar(PastoresDTO pastores) {
		PastoresEntity pastoresEntity = new PastoresEntity(pastores);
		return new PastoresDTO(pastoresRepository.save(pastoresEntity));
	}
	
	public void excluir(Long id) {
		PastoresEntity pastores = pastoresRepository.findById(id).get();
		pastoresRepository.delete(pastores);
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
