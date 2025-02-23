package com.alejandronieves.repository;

import com.alejandronieves.model.Planta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantaRepository extends JpaRepository<Planta, Long> {

    // Query method para filtrar por el ID del Edificio
    List<Planta> findByEdificioId(Long edificioId);
}
