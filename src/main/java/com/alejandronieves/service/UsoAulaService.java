package com.alejandronieves.service;

import com.alejandronieves.model.UsoAula;
import java.util.List;

public interface UsoAulaService {
    List<UsoAula> listAll();
    UsoAula save(UsoAula usoAula);
    UsoAula findById(Long id);
    void deleteById(Long id);
}
