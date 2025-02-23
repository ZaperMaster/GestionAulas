package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.Planta;
import com.alejandronieves.repository.PlantaRepository;
import com.alejandronieves.service.PlantaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlantaServiceImpl implements PlantaService {

    @Autowired
    private PlantaRepository plantaRepository;

    @Override
    public List<Planta> listAll() {
        return plantaRepository.findAll();
    }

    @Override
    public Planta findById(Long id) {
        return plantaRepository.findById(id).orElse(null);
    }

    @Override
    public Planta save(Planta planta) {
        return plantaRepository.save(planta);
    }

    @Override
    public void deleteById(Long id) {
        plantaRepository.deleteById(id);
    }

    @Override
    public List<Planta> findByEdificio(Long edificioId) {
        // Requiere que el repositorio tenga un método "findByEdificioId"
        return plantaRepository.findByEdificioId(edificioId);
    }
}
