
function initConfiguracionPage() {
    console.log("✅ initConfiguracionPage ejecutado");

    const btnEditar = document.getElementById("btnEditar");
    const btnGuardar = document.getElementById("btnGuardar");
    const inputs = document.querySelectorAll("#formConfiguracion input:not([name='rol'])");

    if (!btnEditar || !btnGuardar) {
        console.warn("❌ Botones no encontrados en configuracion.jsp");
        return;
    }

    // Bloquear campos inicialmente
    inputs.forEach(input => input.disabled = true);
    btnGuardar.style.display = "none";

    btnEditar.addEventListener("click", () => {
        inputs.forEach(input => input.disabled = false);
        btnGuardar.style.display = "inline-block";
        btnEditar.style.display = "none";
    });

    btnGuardar.addEventListener("click", (e) => {
        e.preventDefault();

        const nombre = document.getElementById("nombre").value.trim();
        const correo = document.getElementById("correo").value.trim();
        const contrasena = document.getElementById("contrasena").value.trim();

        // Validaciones
        const correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        const contrasenaRegex = /^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\[\]:;<>,.?~\\/-]).{6,}$/;

        if (!correoRegex.test(correo)) {
            mostrarMensaje("Formato de correo inválido.", "error");
            return;
        }

        if (contrasena && !contrasenaRegex.test(contrasena)) {
            mostrarMensaje("La contraseña debe tener al menos 6 caracteres, una mayúscula y un carácter especial.", "error");
            return;
        }

        // AJAX
        const formData = new FormData(document.getElementById("formConfiguracion"));

        fetch("ConfiguracionController", {
            method: "POST",
            body: formData,
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
            .then(res => res.text())
            .then(html => {
                // Cargar la nueva vista actualizada
                const mainContent = document.getElementById("mainContent");
                if (mainContent) {
                    mainContent.innerHTML = html;
                }
                // Vuelve a ejecutar la inicialización
                initConfiguracionPage();
                mostrarMensaje("Datos actualizados correctamente.", "success");
            })
            .catch(err => {
                console.error(err);
                mostrarMensaje("Error al actualizar los datos.", "error");
            });
    });
}

function mostrarMensaje(mensaje, tipo) {
    const existing = document.getElementById("msgConfig");
    if (existing) {
        existing.remove();
    }

    const div = document.createElement("div");
    div.id = "msgConfig";
    div.className = tipo === "success" 
        ? "bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mt-4" 
        : "bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mt-4";
    div.innerHTML = `<strong>${tipo === "success" ? "Éxito" : "Error"}:</strong> ${mensaje}`;

    const form = document.getElementById("formConfiguracion");
    if (form) {
        form.appendChild(div);
    }
}

// ✅ Marcar como cargado
console.log("✅ configuracion.js cargado (una vez)");
