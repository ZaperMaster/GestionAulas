window.addEventListener("load", iniciarLogin);

function iniciarLogin() {
  const usernameInput = document.getElementById("username");
  const passwordInput = document.getElementById("password");
  const form = document.getElementById("loginForm");

  // Validación instantánea y marcado de campo "touched"
  usernameInput.addEventListener("input", () => { 
    usernameInput.classList.add("touched"); 
    validaEmail(usernameInput); 
  });
  passwordInput.addEventListener("input", () => { 
    passwordInput.classList.add("touched"); 
    validaPasswordLogin(passwordInput); 
  });

  form.addEventListener("submit", function(event) {
    usernameInput.classList.add("touched");
    passwordInput.classList.add("touched");

    if(usernameInput.value.trim() === "") {
      actualizarError(usernameInput, "El email es obligatorio.");
    } else {
      validaEmail(usernameInput);
    }

    if(passwordInput.value.trim() === "") {
      actualizarError(passwordInput, "La contraseña es obligatoria.");
    } else {
      validaPasswordLogin(passwordInput);
    }

    if (!form.checkValidity()) {
      event.preventDefault();
    }
  });
}

function validaEmail(emailInput) {
  const email = emailInput.value.trim();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(email)) {
    actualizarError(emailInput, "Email no válido.");
  } else {
    actualizarError(emailInput, "");
  }
}

// Validación de la contraseña: mínimo 8 caracteres, debe contener al menos un número y no puede ser igual al email.
function validaPasswordLogin(passwordInput) {
  const password = passwordInput.value;
  const email = document.getElementById("username").value.trim();
  let message = "";

  if(password.length < 8) {
    message = "La contraseña debe tener al menos 8 caracteres.";
  } else if(!/\d/.test(password)) {
    message = "La contraseña debe contener al menos un número.";
  } else if(password === email) {
    message = "La contraseña no puede ser igual al email.";
  }
  actualizarError(passwordInput, message);
}

// Función para actualizar el mensaje de error y usar setCustomValidity
function actualizarError(input, mensaje) {
  input.setCustomValidity(mensaje);
  const span = document.getElementById(input.id + "Error");
  if (span) {
    span.textContent = mensaje;
  }
  input.reportValidity();
}
