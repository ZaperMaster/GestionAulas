document.addEventListener("DOMContentLoaded", () => {
  const deleteButtons = document.querySelectorAll(".btn-delete");
  const confirmModal = document.getElementById("confirm-modal");
  const confirmBtn = confirmModal.querySelector(".confirm");
  const cancelBtn = confirmModal.querySelector(".cancel");

  let edificioIdToDelete = null;

  // Abrir el modal al hacer clic en "Borrar"
  deleteButtons.forEach((btn) => {
    btn.addEventListener("click", (e) => {
      e.preventDefault();
      edificioIdToDelete = btn.getAttribute("data-id");
      confirmModal.style.display = "flex";
    });
  });

  // Confirmar borrado
  confirmBtn.addEventListener("click", () => {
    // Aquí llamas a tu endpoint para borrar el edificio.
    // Ejemplo:
    // window.location.href = `/edificios/borrar/${edificioIdToDelete}`;
    console.log("Eliminando edificio con ID:", edificioIdToDelete);
    confirmModal.style.display = "none";
  });

  // Cancelar borrado
  cancelBtn.addEventListener("click", () => {
    confirmModal.style.display = "none";
    edificioIdToDelete = null;
  });
});
