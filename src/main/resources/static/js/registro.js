// Arrays de ejemplo para DNIs y emails ya registrados (en producción, la verificación se realiza en el servidor).
const existingDnis = ["12345678Z", "87654321X"];
const existingEmails = ["test@example.com", "demo@example.com"];

window.addEventListener("load", iniciar);

function iniciar() {
  const dniInput = document.getElementById("dni");
  const emailInput = document.getElementById("email");
  const passwordInput = document.getElementById("password");
  const telefonoInput = document.getElementById("telefono");
  const nombreInput = document.getElementById("nombre");
  const apellidoInput = document.getElementById("apellido");
  const formulario = document.getElementById("registroForm");
  
  // Al usar "input" validamos instantáneamente y, además, marcamos el campo como "touched"
  dniInput.addEventListener("input", () => { dniInput.classList.add("touched"); validaDNI(dniInput); });
  emailInput.addEventListener("input", () => { emailInput.classList.add("touched"); validaEmail(emailInput); });
  passwordInput.addEventListener("input", () => { passwordInput.classList.add("touched"); validaPassword(passwordInput); });
  telefonoInput.addEventListener("input", () => { telefonoInput.classList.add("touched"); validaTelefono(telefonoInput); });
  nombreInput.addEventListener("input", () => { nombreInput.classList.add("touched"); validaNombre(nombreInput); });
  apellidoInput.addEventListener("input", () => { apellidoInput.classList.add("touched"); validaApellido(apellidoInput); });
  
  formulario.addEventListener("submit", function(event) {
    // En submit forzamos la validación de todos los campos y agregamos la clase "touched" para que muestren error si es necesario.
    dniInput.classList.add("touched");
    emailInput.classList.add("touched");
    passwordInput.classList.add("touched");
    telefonoInput.classList.add("touched");
    nombreInput.classList.add("touched");
    apellidoInput.classList.add("touched");
    
    if(dniInput.value.trim() === "") actualizarError(dniInput, "El DNI es obligatorio.");
    else validaDNI(dniInput);
    
    if(emailInput.value.trim() === "") actualizarError(emailInput, "El email es obligatorio.");
    else validaEmail(emailInput);
    
    if(passwordInput.value.trim() === "") actualizarError(passwordInput, "La contraseña es obligatoria.");
    else validaPassword(passwordInput);
    
    if(telefonoInput.value.trim() === "") actualizarError(telefonoInput, "El teléfono es obligatorio.");
    else validaTelefono(telefonoInput);
    
    if(nombreInput.value.trim() === "") actualizarError(nombreInput, "El nombre es obligatorio.");
    else validaNombre(nombreInput);
    
    if(apellidoInput.value.trim() === "") actualizarError(apellidoInput, "El apellido es obligatorio.");
    else validaApellido(apellidoInput);
    
    if (!formulario.checkValidity()) {
      event.preventDefault();
    }
  });
}

// Función para actualizar el mensaje de error en el span correspondiente y usar setCustomValidity
function actualizarError(input, mensaje) {
  input.setCustomValidity(mensaje);
  const span = document.getElementById(input.id + "Error");
  if (span) {
    span.textContent = mensaje;
  }
  input.reportValidity();
}

// Validación del DNI: Formato, letra correcta y verificación de duplicado.
function validaDNI(dniInput) {
  const dni = dniInput.value.trim();
  const dniRegex = /^\d{8}[A-Za-z]$/;
  if (!dniRegex.test(dni)) {
    actualizarError(dniInput, "Formato inválido (8 dígitos y 1 letra).");
  } else {
    const numero = dni.substring(0, 8);
    const letra = dni.substring(8).toUpperCase();
    const letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
    const indice = parseInt(numero, 10) % 23;
    if (letrasValidas.charAt(indice) !== letra) {
      actualizarError(dniInput, "DNI no válido.");
    } else if (existingDnis.includes(dni.toUpperCase())) {
      actualizarError(dniInput, "El DNI ya está registrado.");
    } else {
      actualizarError(dniInput, "");
    }
  }
}

// Validación del Email: Formato y duplicados.
function validaEmail(emailInput) {
  const email = emailInput.value.trim();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    actualizarError(emailInput, "Email no válido.");
  } else if (existingEmails.includes(email.toLowerCase())) {
    actualizarError(emailInput, "El email ya está registrado.");
  } else {
    actualizarError(emailInput, "");
  }
}

// Validación de la Contraseña: Mínimo 8 caracteres, con mayúsculas, minúsculas y al menos un número.
function validaPassword(passwordInput) {
  const password = passwordInput.value;
  let message = "";
  if (password.length < 8) {
    message = "La contraseña debe tener al menos 8 caracteres.";
  } else if (!/[A-Z]/.test(password)) {
    message = "Debe contener al menos una letra mayúscula.";
  } else if (!/[a-z]/.test(password)) {
    message = "Debe contener al menos una letra minúscula.";
  } else if (!/\d/.test(password)) {
    message = "Debe contener al menos un número.";
  }
  actualizarError(passwordInput, message);
}

// Validación del Teléfono: Permite opcionalmente un prefijo, seguido de 9 dígitos que deben comenzar por 6 o 7.
function validaTelefono(telefonoInput) {
  const telefono = telefonoInput.value.trim();
  const telefonoRegex = /^(?:\+\d{1,3}\s?)?(\d{9})$/;
  const match = telefono.match(telefonoRegex);
  if (!match) {
    actualizarError(telefonoInput, "Formato inválido. Debe tener 9 dígitos, opcionalmente con prefijo (ej: +34).");
  } else {
    const numero = match[1];
    if (!/^[67]/.test(numero)) {
      actualizarError(telefonoInput, "El número debe comenzar por 6 o 7.");
    } else {
      actualizarError(telefonoInput, "");
    }
  }
}

// Validación del Nombre: Solo letras (incluyendo tildes y ñ) y espacios, máximo 30 caracteres.
function validaNombre(nombreInput) {
  const nombre = nombreInput.value.trim();
  const nombreRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{1,30}$/;
  if (nombre === "") {
    actualizarError(nombreInput, "El nombre es obligatorio.");
  } else if (!nombreRegex.test(nombre)) {
    actualizarError(nombreInput, "Solo se permiten letras y espacios (máx. 30 caracteres).");
  } else {
    actualizarError(nombreInput, "");
  }
}

// Validación del Apellido: Solo letras (incluyendo tildes y ñ) y espacios, máximo 50 caracteres.
function validaApellido(apellidoInput) {
  const apellido = apellidoInput.value.trim();
  const apellidoRegex = /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{1,50}$/;
  if (apellido === "") {
    actualizarError(apellidoInput, "El apellido es obligatorio.");
  } else if (!apellidoRegex.test(apellido)) {
    actualizarError(apellidoInput, "Solo se permiten letras y espacios (máx. 50 caracteres).");
  } else {
    actualizarError(apellidoInput, "");
  }
}
