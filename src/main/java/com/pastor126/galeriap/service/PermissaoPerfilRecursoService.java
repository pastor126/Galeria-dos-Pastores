package com.pastor126.galeriap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.PermissaoPerfilRecursoDTO;
import com.pastor126.galeriap.entity.PermissaoPerfilRecursoEntity;
import com.pastor126.galeriap.repository.PermissaoPerfilRecursoRepository;

@Service
public class PermissaoPerfilRecursoService {

	@Autowired
	private PermissaoPerfilRecursoRepository permissaoPerfilRecursoRepository;
	
	public List<PermissaoPerfilRecursoDTO> listarTodos(){
		List<PermissaoPerfilRecursoEntity> permissaoPerfilRecursos = permissaoPerfilRecursoRepository.findAll();
		return permissaoPerfilRecursos.stream().map(PermissaoPerfilRecursoDTO::new).toList();
	}
	
	public PermissaoPerfilRecursoDTO buscarPorId(Long id){
		return new PermissaoPerfilRecursoDTO(permissaoPerfilRecursoRepository.findById(id).get());
	}
	
	public void inserir(PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
		PermissaoPerfilRecursoEntity permissaoPerfilRecursoEntity = new PermissaoPerfilRecursoEntity(permissaoPerfilRecurso);
		permissaoPerfilRecursoRepository.save(permissaoPerfilRecursoEntity);
	}
	
	public PermissaoPerfilRecursoDTO alterar(PermissaoPerfilRecursoDTO permissaoPerfilRecurso) {
		PermissaoPerfilRecursoEntity permissaoPerfilRecursoEntity = new PermissaoPerfilRecursoEntity(permissaoPerfilRecurso);
		return new PermissaoPerfilRecursoDTO(permissaoPerfilRecursoRepository.save(permissaoPerfilRecursoEntity));
	}
	
	public void excluir(Long id) {
		PermissaoPerfilRecursoEntity permissaoPerfilRecurso = permissaoPerfilRecursoRepository.findById(id).get();
		permissaoPerfilRecursoRepository.delete(permissaoPerfilRecurso);
	}
}


