package com.pastor126.galeriap.entity;

import java.util.Objects;

import com.pastor126.galeriap.dto.AcessDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ACESS_ENTITY")
public class AcessEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String token;
	
	@Column(nullable = false, unique = true)
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public AcessEntity() {
		super();
		// TODO Auto-generated constructor stub
	}


	public AcessEntity(AcessDTO acessDTO) {
		  this.username = acessDTO.getUsername();
		  this.token = acessDTO.getToken();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AcessEntity other = (AcessEntity) obj;
		return Objects.equals(username, other.username);
	}

}
