package com.pastor126.galeriap.dto;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.entity.PastoresEntity;

public class PastoresDTO {
	
	private Long id;	
	private String numero;	
	private String iniciais;
	private String nome;
	
	public PastoresDTO(PastoresEntity pastor) {
		BeanUtils.copyProperties(pastor, this);
	}

	public PastoresDTO() {
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

}