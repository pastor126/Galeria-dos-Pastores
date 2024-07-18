package com.pastor126.galeriap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.RecursosDTO;
import com.pastor126.galeriap.entity.RecursoEntity;
import com.pastor126.galeriap.repository.RecursosRepository;

@Service
public class RecursosService {
	
	@Autowired
	private RecursosRepository recursosRepository;
	
	public List<RecursosDTO> listarTodos(){
		List<RecursoEntity> recursos = recursosRepository.findAll();
		return recursos.stream().map(RecursosDTO::new).toList();
	}
	
	public RecursosDTO buscarPorId(Long id){
		return new RecursosDTO(recursosRepository.findById(id).get());
	}
	
	public void inserir(RecursosDTO recurso) {
		RecursoEntity recursoEntity = new RecursoEntity(recurso);
		recursosRepository.save(recursoEntity);
	}
	
	public RecursosDTO alterar(RecursosDTO recurso) {
		RecursoEntity recursoEntity = new RecursoEntity(recurso);
		return new RecursosDTO(recursosRepository.save(recursoEntity));
	}
	
	public void excluir(Long id) {
		RecursoEntity recurso = recursosRepository.findById(id).get();
		recursosRepository.delete(recurso);
	}
	

}
