package com.alejandronieves.repository;

import com.alejandronieves.model.Aula;
import com.alejandronieves.model.TipoAula;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

	long countByTipoAula(TipoAula tipoAula);
    // Si no necesitas contar por tipoAula, elimina el siguiente método:
    // long countByTipoAula(com.alejandronieves.model.TipoAula tipoAula);

	List<Aula> findByPlantaId(Long plantaId);
	
	
}
