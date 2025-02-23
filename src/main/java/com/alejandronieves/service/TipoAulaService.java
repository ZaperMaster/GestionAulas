package com.alejandronieves.service;

import com.alejandronieves.model.TipoAula;
import java.util.List;

public interface TipoAulaService {
    List<TipoAula> listAll();
    TipoAula save(TipoAula tipoAula);
	TipoAula findById(Long id);
	void deleteById(Long id);
	long countAulasByTipo(Long id);
}
