package com.alejandronieves.repository;

import com.alejandronieves.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Agrega este método para obtener las reservas de un usuario por su ID
    List<Reserva> findByUsuarioId(Long usuarioId);
}
