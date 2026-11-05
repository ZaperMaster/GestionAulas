package com.alejandronieves.repository;

import com.alejandronieves.model.Aula;
import com.alejandronieves.model.TipoAula;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

	long countByTipoAula(TipoAula tipoAula);
    // Si no necesitas contar por tipoAula, elimina el siguiente método:
    // long countByTipoAula(com.alejandronieves.model.TipoAula tipoAula);

	List<Aula> findByPlantaId(Long plantaId);

	// Bloquea el aula para que dos reservas simultáneas no pasen a la vez la comprobación
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from Aula a where a.id = :id")
	Optional<Aula> bloquearPorId(@Param("id") Long id);
	
	
}
