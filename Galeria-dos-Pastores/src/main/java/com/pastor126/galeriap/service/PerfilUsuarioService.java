package com.pastor126.galeriap.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pastor126.galeriap.dto.PerfilUsuarioDTO;
import com.pastor126.galeriap.entity.PerfilUsuarioEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.repository.PerfilUsuarioRepository;

@Service
public class PerfilUsuarioService {

	@Autowired
	private PerfilUsuarioRepository perfilUsuarioRepository;
	
	public List<PerfilUsuarioDTO> listarTodos(){
		List<PerfilUsuarioEntity> perfilUsuarios = perfilUsuarioRepository.findAll();
		return perfilUsuarios.stream().map(PerfilUsuarioDTO::new).toList();
	}
	
	public PerfilUsuarioDTO buscarPorId(Long id){
		return new PerfilUsuarioDTO(perfilUsuarioRepository.findById(id).get());
	}
	
	public PerfilUsuarioDTO buscarPorUsuario(UsuarioEntity usuario) {
	    return new PerfilUsuarioDTO(
	        perfilUsuarioRepository.findByUsuario(usuario)
	        .orElseThrow(() -> new RuntimeException("Perfil não encontrado para o usuário: " + usuario.getLogin()))
	    );
	}

	public void inserir(PerfilUsuarioDTO perfilUsuario) {
		PerfilUsuarioEntity perfilUsuarioEntity = new PerfilUsuarioEntity(perfilUsuario);
		perfilUsuarioRepository.save(perfilUsuarioEntity);
	}
	
	public PerfilUsuarioDTO alterar(PerfilUsuarioDTO perfilUsuario) {
		PerfilUsuarioEntity perfilUsuarioEntity = new PerfilUsuarioEntity(perfilUsuario);
		return new PerfilUsuarioDTO(perfilUsuarioRepository.save(perfilUsuarioEntity));
	}
	
	public void excluir(Long id) {
		PerfilUsuarioEntity perfilUsuario = perfilUsuarioRepository.findById(id)
				 .orElseThrow(() -> new RuntimeException("Perfil não encontrado com ID: " + id));
		perfilUsuarioRepository.delete(perfilUsuario);
	}
	
}
