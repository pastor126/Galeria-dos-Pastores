package com.pastor126.galeriap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.PastoresDTO;
import com.pastor126.galeriap.entity.PastoresEntity;
import com.pastor126.galeriap.repository.PastoresRepository;

@Service
public class PastoresService {

	@Autowired
	PastoresRepository pastoresRepository;
	
	public List<PastoresDTO> listarTodos(){
		List<PastoresEntity> pastores = pastoresRepository.findAll();
		return pastores.stream().map(PastoresDTO :: new).toList();
	}
	
	public PastoresDTO buscarPorId(Long id){
		return new PastoresDTO(pastoresRepository.findById(id).get());
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
	
}
