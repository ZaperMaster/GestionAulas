package com.alejandronieves.service;

import com.alejandronieves.model.Reserva;
import java.util.List;

public interface ReservaService {
    Reserva save(Reserva reserva);

    // Valida la reserva y la guarda; lanza ReservaNoValidaException si no se puede hacer
    Reserva reservar(Reserva reserva);

    List<Reserva> listAll();
    Reserva findById(Long id);
    void deleteById(Long id);
    List<Reserva> listByUserId(Long userioId);

    // Otros métodos, por ejemplo validaciones, etc.
}
