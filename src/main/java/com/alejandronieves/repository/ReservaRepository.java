package com.alejandronieves.repository;

import com.alejandronieves.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Agrega este método para obtener las reservas de un usuario por su ID
    List<Reserva> findByUsuarioId(Long usuarioId);

    // Reservas activas de un aula cuyas fechas y horas se cruzan con las indicadas.
    // Una reserva que empieza justo cuando acaba otra no se considera un choque.
    @Query("select count(r) from Reserva r where r.aula.id = :aulaId"
            + " and (r.activo is null or r.activo = true)"
            + " and r.fechainicio <= :fechaFin and r.fechafin >= :fechaInicio"
            + " and r.horainicio < :horaFin and r.horafin > :horaInicio")
    long contarSolapadas(@Param("aulaId") Long aulaId,
                         @Param("fechaInicio") LocalDate fechaInicio,
                         @Param("fechaFin") LocalDate fechaFin,
                         @Param("horaInicio") LocalTime horaInicio,
                         @Param("horaFin") LocalTime horaFin);
}
