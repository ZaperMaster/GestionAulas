// GestionAulas — comportamiento común de la interfaz
document.addEventListener("DOMContentLoaded", () => {
  // Confirmación antes de enviar formularios destructivos: <form data-confirm="¿Seguro?">
  document.querySelectorAll("form[data-confirm]").forEach((form) => {
    form.addEventListener("submit", (e) => {
      if (!window.confirm(form.dataset.confirm)) e.preventDefault();
    });
  });

  // Menú lateral en pantallas pequeñas
  document.querySelectorAll("[data-toggle-sidebar]").forEach((btn) => {
    btn.addEventListener("click", (e) => {
      e.stopPropagation();
      document.body.classList.toggle("sidebar-open");
    });
  });
  document.addEventListener("click", (e) => {
    if (document.body.classList.contains("sidebar-open") && !e.target.closest("#sidebar")) {
      document.body.classList.remove("sidebar-open");
    }
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") document.body.classList.remove("sidebar-open");
  });

  // Formulario de aula: al cambiar de edificio, cargar solo sus plantas
  const edificio = document.querySelector("[data-plantas-url]");
  const planta = document.getElementById("planta");
  if (edificio && planta) {
    edificio.addEventListener("change", async () => {
      planta.disabled = true;
      planta.innerHTML = '<option value="">Cargando…</option>';
      try {
        const res = await fetch(`${edificio.dataset.plantasUrl}?edificioId=${encodeURIComponent(edificio.value)}`);
        const plantas = res.ok ? await res.json() : [];
        planta.innerHTML = plantas.length
          ? '<option value="" disabled selected>Selecciona una planta</option>'
          : '<option value="" disabled selected>Este edificio no tiene plantas</option>';
        plantas.forEach((p) => planta.add(new Option(p.descripcion, p.id)));
      } catch {
        planta.innerHTML = '<option value="" disabled selected>No se pudieron cargar las plantas</option>';
      }
      planta.disabled = false;
    });
  }

  // Formulario de reserva: fechas y horas coherentes
  const reserva = document.getElementById("reservaForm");
  if (reserva) {
    const fi = reserva.querySelector("#fechainicio");
    const ff = reserva.querySelector("#fechafin");
    const hi = reserva.querySelector("#horainicio");
    const hf = reserva.querySelector("#horafin");
    const today = new Date().toISOString().slice(0, 10);
    fi.min = today;
    const sync = () => {
      ff.min = fi.value || today;
      if (fi.value && (!ff.value || ff.value < fi.value)) ff.value = fi.value;
      ff.setCustomValidity(ff.value && fi.value && ff.value < fi.value ? "La fecha final no puede ser anterior a la inicial." : "");
      hf.setCustomValidity(hi.value && hf.value && hf.value <= hi.value ? "La hora final debe ser posterior a la inicial." : "");
    };
    [fi, ff, hi, hf].forEach((el) => el.addEventListener("input", sync));
    reserva.addEventListener("submit", (e) => {
      sync();
      if (!reserva.checkValidity()) {
        e.preventDefault();
        reserva.reportValidity();
      }
    });
  }
});
