package com.pastor126.galeriap.service;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.PastoresHonorariosDTO;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.PastoresHonorariosEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.PastoresHonorariosRepository;



@Service
public class PastoresHonorariosService {

	@Autowired
	PastoresHonorariosRepository pastoresHonorariosRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PerfilUsuarioService perfilUsuarioService;
	
	   @Autowired
	    private AuthDtoCacheService authDtoCacheService;
	
	
	
	public List<PastoresHonorariosDTO> listarTodos() throws IOException{
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
			 List<PastoresHonorariosEntity> pastores = pastoresHonorariosRepository.findAll();
			return pastores.stream().map(PastoresHonorariosDTO :: new).toList();
	}else {
	
			System.out.println("parou aqui");
			List<PastoresHonorariosEntity> pastores = pastoresHonorariosRepository.findAll();
			for(PastoresHonorariosEntity pastor : pastores) {
				pastor.setNome(null);
			}
			return pastores.stream().map(PastoresHonorariosDTO :: new).toList();
			}	
		
		
	}
	
	
	
	public PastoresHonorariosDTO buscarPorId(Long id){
		return new PastoresHonorariosDTO(pastoresHonorariosRepository.findById(id).get());
	}
	
	public void inserir(PastoresHonorariosDTO pastores) {
		PastoresHonorariosEntity pastoresHonorariosEntity = new PastoresHonorariosEntity(pastores);
		pastoresHonorariosRepository.save(pastoresHonorariosEntity);
	}
	
	public PastoresHonorariosDTO alterar(PastoresHonorariosDTO pastores) {
		PastoresHonorariosEntity pastoresHonorariosEntity = new PastoresHonorariosEntity(pastores);
		return new PastoresHonorariosDTO(pastoresHonorariosRepository.save(pastoresHonorariosEntity));
	}
	
	public void excluir(Long id) {
		PastoresHonorariosEntity pastores = pastoresHonorariosRepository.findById(id).get();
		pastoresHonorariosRepository.delete(pastores);
	}
	
}
