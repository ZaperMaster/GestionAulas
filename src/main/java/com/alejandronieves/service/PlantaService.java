package com.alejandronieves.service;

import com.alejandronieves.model.Planta;
import java.util.List;

public interface PlantaService {
    List<Planta> listAll();
    Planta findById(Long id);
    Planta save(Planta planta);
    void deleteById(Long id);

    // Método para obtener plantas dado el ID del edificio
    List<Planta> findByEdificio(Long edificioId);
}
