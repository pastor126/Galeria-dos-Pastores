package com.pastor126.galeriap.entity.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SituacaoUsuario {

	ATIVO("A", "Ativo"), 
	INATIVO("I", "Inativo"), 
	PENDENTE("P", "Pendente");

	private String codigo;
	private String descricao;

	private SituacaoUsuario(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;

	}

	@JsonValue
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public static SituacaoUsuario valorEnum(String codigo) {
		if(codigo.equals("A")) {
			return ATIVO;
		}else if(codigo.equals("I")) {
			return INATIVO;
		}else if(codigo.equals("P")) {
			return PENDENTE;
		}else {
			return null;
		}
	}
}
