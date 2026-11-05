package com.alejandronieves.serviceImpl;

import com.alejandronieves.repository.AulaRepository;
import com.alejandronieves.service.ReservaNoValidaException;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
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

    @Autowired
    private AulaRepository aulaRepository;

    @Override
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public Reserva reservar(Reserva reserva) {
        validarFechas(reserva);
        aulaRepository.bloquearPorId(reserva.getAula().getId())
                .orElseThrow(() -> new ReservaNoValidaException("El aula ya no existe."));
        long solapadas = reservaRepository.contarSolapadas(reserva.getAula().getId(),
                reserva.getFechainicio(), reserva.getFechafin(),
                reserva.getHorainicio(), reserva.getHorafin());
        if (solapadas > 0) {
            throw new ReservaNoValidaException("El aula ya está reservada en esas fechas y horas. Elige otro horario.");
        }
        return reservaRepository.save(reserva);
    }

    private void validarFechas(Reserva reserva) {
        if (reserva.getFechainicio() == null || reserva.getFechafin() == null
                || reserva.getHorainicio() == null || reserva.getHorafin() == null) {
            throw new ReservaNoValidaException("Indica las fechas y las horas de la reserva.");
        }
        if (reserva.getFechainicio().isBefore(LocalDate.now())) {
            throw new ReservaNoValidaException("La fecha de inicio no puede ser anterior a hoy.");
        }
        if (reserva.getFechafin().isBefore(reserva.getFechainicio())) {
            throw new ReservaNoValidaException("La fecha final no puede ser anterior a la inicial.");
        }
        if (!reserva.getHorafin().isAfter(reserva.getHorainicio())) {
            throw new ReservaNoValidaException("La hora de fin debe ser posterior a la de inicio.");
        }
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
		return reservaRepository.findByUsuarioId(userioId);
	}
}
