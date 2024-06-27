package com.pastor126.galeriap.dto;

import org.springframework.beans.BeanUtils;

import com.pastor126.galeriap.entity.PastoresEntity;

public class PastoresDTO {
	
	private Long id;
	
	private int numero;	
	
	private String inicias;	
	
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


	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}


	public String getInicias() {
		return inicias;
	}


	public void setInicias(String inicias) {
		this.inicias = inicias;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	



}