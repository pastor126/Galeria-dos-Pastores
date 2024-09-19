package com.pastor126.galeriap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastor126.galeriap.entity.PerfilUsuarioEntity;
import com.pastor126.galeriap.entity.UsuarioEntity;


public interface PerfilUsuarioRepository extends JpaRepository<PerfilUsuarioEntity, Long>{
	

	Optional<PerfilUsuarioEntity> findById(Long id);

	Optional<PerfilUsuarioEntity> findByUsuario(UsuarioEntity usuario);


}
