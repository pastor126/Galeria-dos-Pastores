package com.pastor126.galeriap.dto;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.entity.PermissaoPerfilRecursoEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PermissaoPerfilRecursoDTO {

	private Long id;
	private PerfilDTO perfil;
	private RecursosDTO recurso;
	
	public PermissaoPerfilRecursoDTO(PermissaoPerfilRecursoEntity permissaoPerfilRecurso) {
		BeanUtils.copyProperties(permissaoPerfilRecurso, this);
		if(permissaoPerfilRecurso != null && permissaoPerfilRecurso.getPerfil() != null) {	
			this.perfil = new PerfilDTO(permissaoPerfilRecurso.getPerfil());
		}
		if(permissaoPerfilRecurso != null && permissaoPerfilRecurso.getRecurso() != null) {	
			this.recurso = new RecursosDTO(permissaoPerfilRecurso.getRecurso());
		}
	}
}






	

