package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.FamCurso;
import com.alejandronieves.repository.FamCursoRepository;
import com.alejandronieves.service.FamCursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamCursoServiceImpl implements FamCursoService {

    @Autowired
    private FamCursoRepository famCursoRepository;

    @Override
    public List<FamCurso> listAll() {
        return famCursoRepository.findAll();
    }

    @Override
    public FamCurso save(FamCurso famCurso) {
        return famCursoRepository.save(famCurso);
    }

    @Override
    public FamCurso findById(Long id) {
        return famCursoRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        famCursoRepository.deleteById(id);
    }
}
