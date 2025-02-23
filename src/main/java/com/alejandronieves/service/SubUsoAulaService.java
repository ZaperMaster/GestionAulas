package com.alejandronieves.service;

import com.alejandronieves.model.SubUsoAula;
import java.util.List;

public interface SubUsoAulaService {
    List<SubUsoAula> listAll();
    SubUsoAula save(SubUsoAula subUsoAula);
    SubUsoAula findById(Long id);
    void deleteById(Long id);
}
