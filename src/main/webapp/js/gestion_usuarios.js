
document.addEventListener('DOMContentLoaded', function () {
    const modalEliminar = document.getElementById('modalEliminarUsuario');
    if (!modalEliminar) return;

    modalEliminar.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const userId = button.getAttribute('data-user-id');
        const input = modalEliminar.querySelector('#idUsuarioEliminar');
        input.value = userId;
    });
    
});
