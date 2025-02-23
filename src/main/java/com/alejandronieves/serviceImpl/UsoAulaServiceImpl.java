package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.UsoAula;
import com.alejandronieves.repository.UsoAulaRepository;
import com.alejandronieves.service.UsoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsoAulaServiceImpl implements UsoAulaService {

    @Autowired
    private UsoAulaRepository usoAulaRepository;

    @Override
    public List<UsoAula> listAll() {
        return usoAulaRepository.findAll();
    }

    @Override
    public UsoAula save(UsoAula usoAula) {
        return usoAulaRepository.save(usoAula);
    }

    @Override
    public UsoAula findById(Long id) {
        return usoAulaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        usoAulaRepository.deleteById(id);
    }
}
