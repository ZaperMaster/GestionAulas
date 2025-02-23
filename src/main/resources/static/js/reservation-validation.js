// reservation-validation.js
function validateReservation() {
    const fechaDesde = document.getElementById('fechaDesde').value;
    const horaDesde = document.getElementById('horaDesde').value;
    const fechaHasta = document.getElementById('fechaHasta').value;
    const horaHasta = document.getElementById('horaHasta').value;
    const errorDiv = document.getElementById('errorMessages');
    errorDiv.innerHTML = "";

    // Verifica que todos los campos tengan valor
    if (!fechaDesde || !horaDesde || !fechaHasta || !horaHasta) {
        errorDiv.innerHTML = "Todos los campos de fecha y hora son obligatorios.";
        return false;
    }

    // Construir objetos Date a partir de los valores
    const start = new Date(fechaDesde + "T" + horaDesde);
    const end = new Date(fechaHasta + "T" + horaHasta);
    const now = new Date();

    console.log("Inicio:", start);
    console.log("Fin:", end);
    console.log("Ahora:", now);

    // La reserva no debe iniciar en el pasado
    if (start < now) {
        errorDiv.innerHTML = "La fecha y hora de inicio no pueden ser en el pasado.";
        return false;
    }

    // La fecha de fin debe ser posterior a la de inicio
    if (end <= start) {
        errorDiv.innerHTML = "La fecha y hora de fin deben ser posteriores a la fecha y hora de inicio.";
        return false;
    }

    // (Opcional) Verifica que la reserva no haya terminado ya
    if (end < now) {
        errorDiv.innerHTML = "La reserva ya ha finalizado.";
        return false;
    }

    return true;
}
