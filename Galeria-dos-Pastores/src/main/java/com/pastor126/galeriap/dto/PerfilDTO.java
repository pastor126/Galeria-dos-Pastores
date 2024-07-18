package com.pastor126.galeriap.dto;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.entity.PerfilEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PerfilDTO {

	private Long id;
	private String descricao;
	
	public PerfilDTO(PerfilEntity perfil) {
		BeanUtils.copyProperties(perfil, this);
	}
	
}
