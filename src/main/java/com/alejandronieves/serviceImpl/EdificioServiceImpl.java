package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.Edificio;
import com.alejandronieves.repository.EdificioRepository;
import com.alejandronieves.service.EdificioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EdificioServiceImpl implements EdificioService {

    @Autowired
    private EdificioRepository edificioRepository;
    
    @Override
    public List<Edificio> listAll() {
        return edificioRepository.findAll();
    }
    
    @Override
    public Edificio save(Edificio edificio) {
        return edificioRepository.save(edificio);
    }
    
    @Override
    public Edificio findById(Long id) {
        return edificioRepository.findById(id).orElse(null);
    }
    
    @Override
    public void deleteById(Long id) {
        edificioRepository.deleteById(id);
    }
}
