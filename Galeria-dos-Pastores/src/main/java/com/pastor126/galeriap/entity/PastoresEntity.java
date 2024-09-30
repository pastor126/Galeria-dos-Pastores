package com.pastor126.galeriap.entity;

import java.util.Objects;
import org.springframework.beans.BeanUtils;
import com.pastor126.galeriap.dto.PastoresDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PASTORES_ENTITY")
public class PastoresEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String numero;
	
	@Column(nullable = false)
	private String iniciais;
	
	@Column(nullable = false)
	private String nome;
		
	public PastoresEntity(PastoresDTO pastor) {
		BeanUtils.copyProperties(pastor, this);
	}

	public PastoresEntity() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getIniciais() {
		return iniciais;
	}

	public void setIniciais(String iniciais) {
		this.iniciais = iniciais;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
		PastoresEntity other = (PastoresEntity) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
