package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.SubUsoAula;
import com.alejandronieves.repository.SubUsoAulaRepository;
import com.alejandronieves.service.SubUsoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubUsoAulaServiceImpl implements SubUsoAulaService {

    @Autowired
    private SubUsoAulaRepository subUsoAulaRepository;

    @Override
    public List<SubUsoAula> listAll() {
        return subUsoAulaRepository.findAll();
    }

    @Override
    public SubUsoAula save(SubUsoAula subUsoAula) {
        return subUsoAulaRepository.save(subUsoAula);
    }

    @Override
    public SubUsoAula findById(Long id) {
        return subUsoAulaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        subUsoAulaRepository.deleteById(id);
    }
}
