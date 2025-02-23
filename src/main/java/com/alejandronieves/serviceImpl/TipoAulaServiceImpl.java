package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.TipoAula;
import com.alejandronieves.repository.TipoAulaRepository;
import com.alejandronieves.service.TipoAulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoAulaServiceImpl implements TipoAulaService {

    @Autowired
    private TipoAulaRepository tipoAulaRepository;
    
    @Override
    public List<TipoAula> listAll() {
        return tipoAulaRepository.findAll();
    }
    
    @Override
    public TipoAula save(TipoAula tipoAula) {
        return tipoAulaRepository.save(tipoAula);
    }
    
    @Override
    public TipoAula findById(Long id) {
        return tipoAulaRepository.findById(id).orElse(null);
    }
    
    @Override
    public void deleteById(Long id) {
        tipoAulaRepository.deleteById(id);
    }

	@Override
	public long countAulasByTipo(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}
}
