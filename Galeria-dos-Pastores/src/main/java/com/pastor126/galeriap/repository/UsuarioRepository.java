package com.pastor126.galeriap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastor126.galeriap.entity.UsuarioEntity;


public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long>{
	
	Optional<UsuarioEntity> findByLogin(String login);

}
