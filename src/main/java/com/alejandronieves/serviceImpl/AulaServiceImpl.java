package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.Aula;
import com.alejandronieves.repository.AulaRepository;
import com.alejandronieves.service.AulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AulaServiceImpl implements AulaService {

    @Autowired
    private AulaRepository aulaRepository;

    @Override
    public List<Aula> listAll() {
        return aulaRepository.findAll();
    }

    @Override
    public Aula findById(Long id) {
        return aulaRepository.findById(id).orElse(null);
    }

    @Override
    public Aula save(Aula aula) {
        return aulaRepository.save(aula);
    }

    @Override
    public void deleteById(Long id) {
        aulaRepository.deleteById(id);
    }

    @Override
    public List<Aula> findByPlantaId(Long plantaId) {
        return aulaRepository.findByPlantaId(plantaId);
    }

	@Override
	public List<Aula> findByUsuarioId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}  
}

