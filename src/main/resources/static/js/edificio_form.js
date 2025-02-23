document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("edificioForm");
  const descripcionInput = document.getElementById("descripcion");
  const ubicacionInput = document.getElementById("ubicacion");
  const naulasInput = document.getElementById("naulas");
  const npuertasInput = document.getElementById("npuertasacceso");
  const activoSelect = document.getElementById("activo");

  // Asignar eventos de validación a cada campo
  descripcionInput.addEventListener("input", validarDescripcion);
  ubicacionInput.addEventListener("input", validarUbicacion);
  naulasInput.addEventListener("input", validarNaulas);
  npuertasInput.addEventListener("input", validarNpuertas);
  activoSelect.addEventListener("change", () =>
    validarSelect(activoSelect, "⚠️ Seleccione una opción.")
  );

  form.addEventListener("submit", function (event) {
    let isValid = true;
    if (!validarDescripcion()) isValid = false;
    if (!validarUbicacion()) isValid = false;
    if (!validarNaulas()) isValid = false;
    if (!validarNpuertas()) isValid = false;
    if (!validarSelect(activoSelect, "⚠️ Seleccione una opción."))
      isValid = false;

    if (!isValid) {
      event.preventDefault();
    } else {
      if (!confirm("📌 ¿Estás seguro de guardar este Edificio?")) {
        event.preventDefault();
      }
    }
  });

  function validarDescripcion() {
    const valor = descripcionInput.value.trim();
    const regex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]+$/;

    if (valor.length < 3) {
      mostrarError(descripcionInput, "⚠️ Mínimo 3 caracteres.");
      return false;
    }

    // Verifica que contenga al menos una vocal.
    const vowelRegex = /[AEIOUÁÉÍÓÚaeiouáéíóú]/;
    if (!vowelRegex.test(valor)) {
      mostrarError(
        descripcionInput,
        "⚠️ La descripción debe contener al menos una vocal."
      );
      return false;
    }

    if (!regex.test(valor)) {
      mostrarError(descripcionInput, "⚠️ Solo letras y espacios permitidos.");
      return false;
    }

    limpiarError(descripcionInput);
    return true;
  }

  function validarUbicacion() {
    const valor = ubicacionInput.value.trim();
    if (valor.length < 3) {
      mostrarError(ubicacionInput, "⚠️ Mínimo 3 caracteres.");
      return false;
    }
    limpiarError(ubicacionInput);
    return true;
  }

  function validarNaulas() {
    const valor = naulasInput.value;
    if (valor === "") {
      limpiarError(naulasInput);
      return true;
    }
    if (parseInt(valor) < 0) {
      mostrarError(naulasInput, "⚠️ Debe ser un número mayor o igual a 0.");
      return false;
    }
    limpiarError(naulasInput);
    return true;
  }

  function validarNpuertas() {
    const valor = npuertasInput.value;
    if (valor === "") {
      limpiarError(npuertasInput);
      return true;
    }
    if (parseInt(valor) < 0) {
      mostrarError(npuertasInput, "⚠️ Debe ser un número mayor o igual a 0.");
      return false;
    }
    limpiarError(npuertasInput);
    return true;
  }

  function validarSelect(select, mensaje) {
    if (!select.value) {
      mostrarError(select, mensaje);
      return false;
    }
    limpiarError(select);
    return true;
  }

  function mostrarError(input, mensaje) {
    input.style.border = "2px solid red";
    input.nextElementSibling.textContent = mensaje;
  }

  function limpiarError(input) {
    input.style.border = "1px solid var(--border-color)";
    input.nextElementSibling.textContent = "";
  }
});
