package com.pastor126.galeriap.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pastor126.galeriap.dto.AcessDTO;
import com.pastor126.galeriap.entity.AcessEntity;
import com.pastor126.galeriap.repository.AcessRepository;

@Service
public class AcessService {
	
	@Autowired
	private AcessRepository acessRepository;
	
	public void inserir(AcessDTO acessDTO) {
		AcessEntity acess = new AcessEntity(acessDTO);
		acess.setId(null);
		acessRepository.save(acess);
	}
	
	public AcessDTO alterar(Long id, AcessDTO acess) throws IOException {
		 Optional<AcessEntity> optionalUsuario = acessRepository.findById(id);
	
			AcessEntity usuarioedit = optionalUsuario.get();
	        usuarioedit.setUsername(acess.getUsername()); 
	        usuarioedit.setToken(acess.getToken());
	        usuarioedit.setId(acess.getId());
	        AcessEntity atual = acessRepository.save(usuarioedit);

	        // Converte a entidade para DTO e retorna
	        return new AcessDTO(atual);	
	}
	
	
	
	public void excluir(Long id) throws IOException {
		acessRepository.deleteById(id);
	}
	
	public AcessDTO buscarPorId(Long id) throws IOException {
		return new AcessDTO(acessRepository.findById(id).get());
	}
	
	
	 public AcessDTO findByUsername(String login) throws IOException, RuntimeException {
			Optional<AcessEntity> acessOpt = acessRepository.findByUsername(login);
			AcessEntity acess = acessOpt.orElseThrow(() -> new RuntimeException("Acesso não encontrado"));
	        return  new AcessDTO(acess);
	  }
	
	
	
	

}
