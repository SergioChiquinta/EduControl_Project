
function initAsignaturasPage() {
    console.log("✅ initAsignaturasPage ejecutado");

    const btnNueva = document.getElementById("btnNuevaAsignatura");
    const modal = document.getElementById("modalAsignatura");
    const form = document.getElementById("formAsignatura");
    const btnCerrar = document.getElementById("btnCerrarModal");

    const modalEliminar = document.getElementById("modalConfirmarEliminar");
    let asigIdAEliminar = null;

    // Abrir modal nueva
    btnNueva.addEventListener("click", () => {
        document.getElementById("asigId").value = "";
        document.getElementById("asigNombre").value = "";
        modal.classList.remove("hidden");
    });

    // Cerrar modal
    btnCerrar.addEventListener("click", () => {
        modal.classList.add("hidden");
    });

    // Editar
    document.querySelectorAll(".btnEditar").forEach(btn => {
        btn.addEventListener("click", () => {
            // Datos actuales
            const id = btn.dataset.id;
            const nombre = btn.dataset.nombre;
            const docenteIdActual = btn.dataset.docenteid;
            const docenteNombreActual = btn.closest("tr").querySelector("td:nth-child(3)").innerText;

            // Limpiar el combo antes
            const selectDocente = document.getElementById("asigDocente");
            const opcionesOriginales = selectDocente.innerHTML; // Guardar opciones originales (docentes libres)

            // Crear opción actual al inicio
            const optionActual = document.createElement("option");
            optionActual.value = docenteIdActual;
            optionActual.textContent = `${docenteNombreActual} (Docente Actual)`;
            optionActual.selected = true;

            // Resetear y agregar
            selectDocente.innerHTML = "";
            selectDocente.appendChild(optionActual);

            // Insertar las opciones originales (docentes libres)
            const tempDiv = document.createElement("div");
            tempDiv.innerHTML = opcionesOriginales;
            tempDiv.querySelectorAll("option").forEach(opt => {
                if (opt.value !== docenteIdActual) {
                    selectDocente.appendChild(opt);
                }
            });

            // Setear campos
            document.getElementById("asigId").value = id;
            document.getElementById("asigNombre").value = nombre;

            // Mostrar modal
            document.getElementById("modalAsignatura").classList.remove("hidden");
        });
    });

    // Enviar formulario crear/editar
    form.addEventListener("submit", e => {
        e.preventDefault();
        const formData = new FormData(form);
        formData.append("accion", document.getElementById("asigId").value ? "editar" : "crear");

        fetch("AsignaturasAdminController", {
            method: "POST",
            body: formData
        }).then(res => res.text())
                .then(html => {
                    document.getElementById("mainContent").innerHTML = html;
                    initAsignaturasPage();
                });
    });

    // Eliminar
    document.querySelectorAll(".btnEliminar").forEach(btn => {
        btn.addEventListener("click", () => {
            asigIdAEliminar = btn.dataset.id;
            modalEliminar.classList.remove("hidden");
        });
    });

    document.getElementById("btnCancelarEliminar").onclick = () => {
        modalEliminar.classList.add("hidden");
        asigIdAEliminar = null;
    };

    document.getElementById("btnConfirmarEliminar").onclick = () => {
        if (asigIdAEliminar) {
            const formData = new FormData();
            formData.append("accion", "eliminar");
            formData.append("id", asigIdAEliminar);

            fetch("AsignaturasAdminController", {
                method: "POST",
                body: formData
            }).then(res => res.text())
                    .then(html => {
                        document.getElementById("mainContent").innerHTML = html;
                        initAsignaturasPage();
                    })
                    .finally(() => {
                        modalEliminar.classList.add("hidden");
                        asigIdAEliminar = null;
                    });
        }
    };
}

console.log("✅ adminAsignaturas.js cargado");
