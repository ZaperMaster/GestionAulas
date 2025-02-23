package com.alejandronieves.service;

import com.alejandronieves.model.FamCurso;
import java.util.List;

public interface FamCursoService {
    List<FamCurso> listAll();
    FamCurso save(FamCurso famCurso);
    FamCurso findById(Long id);
    void deleteById(Long id);
}
