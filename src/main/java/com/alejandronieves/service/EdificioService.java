package com.alejandronieves.service;

import com.alejandronieves.model.Edificio;
import java.util.List;

public interface EdificioService {
    List<Edificio> listAll();
    Edificio save(Edificio edificio);
    Edificio findById(Long id);
    void deleteById(Long id);
}
