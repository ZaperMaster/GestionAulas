document.addEventListener("DOMContentLoaded", () => {
  const deleteButtons = document.querySelectorAll(".btn-delete");
  const confirmModal = document.getElementById("confirm-modal");
  const confirmBtn = confirmModal.querySelector(".confirm");
  const cancelBtn = confirmModal.querySelector(".cancel");

  let aulaIdAEliminar = null;

  // Abrir el modal al hacer clic en "Borrar"
  deleteButtons.forEach((btn) => {
    btn.addEventListener("click", (e) => {
      e.preventDefault();
      aulaIdAEliminar = btn.getAttribute("data-id");
      confirmModal.style.display = "flex";
    });
  });

  // Confirmar borrado
  confirmBtn.addEventListener("click", () => {
    // Aquí llamarías a tu endpoint para borrar el aula con ID = aulaIdAEliminar
    // Ejemplo:
    // window.location.href = `/aulas/borrar/${aulaIdAEliminar}`;
    console.log("Eliminando aula con ID:", aulaIdAEliminar);
    confirmModal.style.display = "none";
  });

  // Cancelar borrado
  cancelBtn.addEventListener("click", () => {
    confirmModal.style.display = "none";
    aulaIdAEliminar = null;
  });

  // Cerrar el modal al hacer clic fuera del contenido
  window.addEventListener("click", (e) => {
    if (e.target === confirmModal) {
      confirmModal.style.display = "none";
      aulaIdAEliminar = null;
    }
  });

  // Cerrar el modal con la tecla ESC
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && confirmModal.style.display === "flex") {
      confirmModal.style.display = "none";
      aulaIdAEliminar = null;
    }
  });
});
