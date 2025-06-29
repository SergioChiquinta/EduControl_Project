
function initCursosPage() {
    console.log("✅ initCursosPage ejecutado");

    const btnNuevo = document.getElementById("btnNuevoCurso");
    const formCrear = document.getElementById("formCrearCurso");

    btnNuevo.addEventListener("click", () => {
        formCrear.style.display = formCrear.style.display === "none" ? "block" : "none";
    });

    // Crear
    formCrear.addEventListener("submit", e => {
        e.preventDefault();
        const formData = new FormData(formCrear);
        formData.append("accion", "crear");

        fetch("CursosAdminController", {
            method: "POST",
            body: formData
        }).then(res => res.text())
          .then(html => {
            document.getElementById("mainContent").innerHTML = html;
            initCursosPage();
          });
    });

    // Eliminar
    let cursoIdEliminar = null;

    document.querySelectorAll(".btnEliminar").forEach(btn => {
        btn.addEventListener("click", () => {
            cursoIdEliminar = btn.dataset.id;
            document.getElementById("modalConfirmarEliminar").classList.remove("hidden");
        });
    });

    document.getElementById("btnCancelarEliminar").onclick = () => {
        document.getElementById("modalConfirmarEliminar").classList.add("hidden");
        cursoIdEliminar = null;
    };

    document.getElementById("btnConfirmarEliminar").onclick = () => {
        if (cursoIdEliminar) {
            const formData = new FormData();
            formData.append("accion", "eliminar");
            formData.append("id", cursoIdEliminar);

            fetch("CursosAdminController", {
                method: "POST",
                body: formData
            }).then(res => res.text())
              .then(html => {
                document.getElementById("mainContent").innerHTML = html;
                initCursosPage();
              })
              .finally(() => {
                document.getElementById("modalConfirmarEliminar").classList.add("hidden");
                cursoIdEliminar = null;
              });
        }
    };
}

console.log("✅ adminCursos.js cargado");
