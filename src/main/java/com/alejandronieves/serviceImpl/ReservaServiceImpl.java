package com.alejandronieves.serviceImpl;

import com.alejandronieves.model.Reserva;
import com.alejandronieves.repository.ReservaRepository;
import com.alejandronieves.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> listAll() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        reservaRepository.deleteById(id);
    }

	@Override
	public List<Reserva> listByUserId(Long userioId) {
		// TODO Auto-generated method stub
		return null;
	}
}
