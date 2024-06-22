package com.pastor126.galeriap.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pastor126.galeriap.entity.UsuarioEntity;
import com.pastor126.galeriap.entity.VerificadorPendenciaEntity;

public interface VerificadorPendenciaRepository extends JpaRepository<VerificadorPendenciaEntity, Long>{
	
	public Optional<VerificadorPendenciaEntity>findByUuid(UUID uuid);
}
