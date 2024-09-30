package com.pastor126.galeriap.entity;

import java.util.Objects;
import org.springframework.beans.BeanUtils;
import com.pastor126.galeriap.dto.PerfilDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Perfil")
public class PerfilEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String descricao;
	
	public PerfilEntity(PerfilDTO perfil) {
		BeanUtils.copyProperties(perfil, this);
	}

	public PerfilEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilEntity other = (PerfilEntity) obj;
		return Objects.equals(id, other.id);
	}
}
