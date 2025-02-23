package com.alejandronieves.service;

import com.alejandronieves.model.Curso;
import java.util.List;

public interface CursoService {
    List<Curso> listAll();
    Curso save(Curso curso);
    Curso findById(Long id);
    void deleteById(Long id);
}
