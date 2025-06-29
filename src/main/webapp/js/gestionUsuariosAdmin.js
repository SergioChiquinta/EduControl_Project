
function initGestionUsuariosPage() {
    console.log("✅ initGestionUsuariosPage ejecutado");

    const formCrear = document.getElementById("formCrear");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const rolSelect = document.getElementById("rolCrear");
    const divSalon = document.getElementById("divSalon");
    const formFiltro = document.getElementById("formFiltro");

    if (!formCrear || !btnMostrarForm || !rolSelect || !formFiltro) {
        console.warn("❌ Elementos no encontrados en gestión usuarios");
        return;
    }

    // Mostrar/ocultar formulario crear
    btnMostrarForm.addEventListener("click", () => {
        formCrear.style.display = formCrear.style.display === "none" ? "block" : "none";
    });

    // Mostrar salón si rol es estudiante
    rolSelect.addEventListener("change", () => {
        if (rolSelect.value === "estudiante") {
            divSalon.style.display = "block";
        } else {
            divSalon.style.display = "none";
        }
    });

    // Validar correo al crear
    formCrear.addEventListener("submit", e => {
        const correo = document.getElementById("correoNuevo").value.trim();
        const correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!correoRegex.test(correo)) {
            e.preventDefault();
            mostrarMensaje("El correo ingresado no tiene un formato válido.", "error", "formCrear");
            return;
        }

        e.preventDefault();

        const formData = new FormData(formCrear);
        formData.append("accion", "crear");

        fetch("GestionUsuariosController", {
            method: "POST",
            body: formData
        }).then(res => res.text())
          .then(html => {
            document.getElementById("mainContent").innerHTML = html;
            initGestionUsuariosPage();
          });
    });

    // Filtro
    formFiltro.addEventListener("submit", e => {
        e.preventDefault();
        const campo = document.getElementById("campoFiltro").value;
        const valor = document.getElementById("valorFiltro").value;

        const params = new URLSearchParams();
        if (campo) params.append("campo", campo);
        if (valor) params.append("valor", valor);

        fetch("GestionUsuariosController?" + params.toString(), {
            method: "GET"
        }).then(res => res.text())
          .then(html => {
            document.getElementById("mainContent").innerHTML = html;
            initGestionUsuariosPage();
          });
    });

    // Editar
    document.querySelectorAll(".btnEditar").forEach(btn => {
        btn.addEventListener("click", () => {
            document.getElementById("modalEditar").style.display = "flex";
            document.getElementById("editarId").value = btn.dataset.id;
            document.getElementById("editarNombre").value = btn.dataset.nombre;
            document.getElementById("editarCorreo").value = btn.dataset.correo;
        });
    });

    document.getElementById("btnCerrarModal").addEventListener("click", () => {
        document.getElementById("modalEditar").style.display = "none";
    });

    document.getElementById("formEditar").addEventListener("submit", e => {
        e.preventDefault();

        const correo = document.getElementById("editarCorreo").value.trim();
        const correoRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

        if (!correoRegex.test(correo)) {
            mostrarMensaje("El correo ingresado no tiene un formato válido.", "error", "formEditar");
            return;
        }

        const formData = new FormData(e.target);
        formData.append("accion", "editar");

        fetch("GestionUsuariosController", {
            method: "POST",
            body: formData
        }).then(res => res.text())
          .then(html => {
            document.getElementById("mainContent").innerHTML = html;
            initGestionUsuariosPage();
          });
    });

    // Modal para confirmar eliminar (nuevo enfoque robusto)
    document.querySelectorAll(".btnEliminar").forEach(btn => {
        btn.addEventListener("click", () => {
            const id = btn.dataset.id;
            const btnConfirmar = document.getElementById("btnConfirmarEliminar");
            btnConfirmar.setAttribute("data-id", id);
            document.getElementById("modalConfirmarEliminar").classList.remove("hidden");
        });
    });

    document.getElementById("btnCancelarEliminar").onclick = () => {
        document.getElementById("modalConfirmarEliminar").classList.add("hidden");
        document.getElementById("btnConfirmarEliminar").removeAttribute("data-id");
    };

    document.getElementById("btnConfirmarEliminar").onclick = () => {
        const id = document.getElementById("btnConfirmarEliminar").getAttribute("data-id");

        if (id) {
            const formData = new FormData();
            formData.append("accion", "eliminar");
            formData.append("id", id);

            fetch("GestionUsuariosController", {
                method: "POST",
                body: formData
            }).then(res => res.text())
              .then(html => {
                document.getElementById("mainContent").innerHTML = html;
                initGestionUsuariosPage();
              })
              .finally(() => {
                document.getElementById("modalConfirmarEliminar").classList.add("hidden");
                document.getElementById("btnConfirmarEliminar").removeAttribute("data-id");
              });
        }
    };
}

function mostrarMensaje(mensaje, tipo, formId) {
    const form = document.getElementById(formId);
    if (!form) return;

    const div = document.createElement("div");
    div.className = tipo === "success"
        ? "bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mt-2"
        : "bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mt-2";
    div.innerHTML = `<strong>${tipo === "success" ? "Éxito" : "Error"}:</strong> ${mensaje}`;

    form.appendChild(div);
    setTimeout(() => div.remove(), 4000);
}

console.log("✅ gestionUsuariosAdmin.js cargado");
