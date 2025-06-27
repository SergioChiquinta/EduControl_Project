
function initReportesPage() {
    console.log("✅ initReportesPage ejecutado");

    const btn = document.getElementById("btnNuevoReporte");
    const form = document.getElementById("formNuevoReporte");
    const periodo = document.getElementById("selectPeriodo");
    const salon = document.getElementById("selectSalon");
    const estudiante = document.getElementById("selectEstudiante");

    if (btn && form) {
        console.log("✅ Botón y formulario encontrados");
        btn.addEventListener("click", () => {
            form.style.display = form.style.display === "none" || form.style.display === "" ? "block" : "none";
        });
    } else {
        console.warn("❌ Botón o formulario no encontrados");
    }

    if (periodo && salon && estudiante) {
        fetch("DatosAcademicosController?accion=periodos")
                .then(res => res.json())
                .then(data => {
                    periodo.innerHTML = "<option value=''>Seleccione</option>";
                    data.forEach(p => {
                        periodo.innerHTML += `<option value="${p.id}">${p.nombre}</option>`;
                    });
                });

        periodo.addEventListener("change", () => {
            fetch(`DatosAcademicosController?accion=salones&periodoId=${periodo.value}`)
                    .then(res => res.json())
                    .then(data => {
                        salon.innerHTML = "<option value=''>Seleccione</option>";
                        data.forEach(s => {
                            salon.innerHTML += `<option value="${s.id}">${s.nombre}</option>`;
                        });
                    });
            estudiante.innerHTML = "<option value=''>Seleccione</option>";
        });

        salon.addEventListener("change", () => {
            fetch(`DatosAcademicosController?accion=estudiantes&salonId=${salon.value}`)
                    .then(res => res.json())
                    .then(data => {
                        estudiante.innerHTML = "<option value=''>Seleccione</option>";
                        data.forEach(e => {
                            estudiante.innerHTML += `<option value="${e.id}">${e.nombre}</option>`;
                        });
                    });
        });
    } else {
        console.warn("❌ No se encontraron los selects");
    }

    const formGenerar = document.getElementById("formGenerarReporte");

    if (formGenerar) {
        formGenerar.addEventListener("submit", async (e) => {
            e.preventDefault();

            const formData = new FormData(formGenerar);
            formData.append("accion", "generar");

            const response = await fetch("ReporteController", {
                method: "POST",
                headers: {
                    'X-Requested-With': 'XMLHttpRequest' // para distinguir solicitudes AJAX
                },
                body: formData
            });

            if (!response.ok) {
                alert("❌ Error al generar el reporte.");
                return;
            }

            const html = await response.text();

            // Cargar solo la parte de reportes en el contenedor dinámico
            const mainContent = document.getElementById("mainContent");
            if (mainContent) {
                mainContent.innerHTML = html;
            }

            // Vuelve a ejecutar los scripts y listeners
            initReportesPage();
        });
    }

}

if (!window.initReportesPageAlreadyCalled) {
    window.initReportesPageAlreadyCalled = true;
    console.log("✅ reportes.js cargado (una vez)");
    initReportesPage();
}
