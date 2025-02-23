package com.alejandronieves.service;

import com.alejandronieves.model.Aula;
import java.util.List;

public interface AulaService {
    List<Aula> listAll();
    Aula save(Aula aula);
    Aula findById(Long id);
    void deleteById(Long id);
	List<Aula> findByPlantaId(Long plantaId);
	List<Aula> findByUsuarioId(Long id);
	
}
