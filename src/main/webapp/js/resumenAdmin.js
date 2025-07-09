
document.addEventListener('DOMContentLoaded', function () {
    // Actualizar cada 10 segundos
    setInterval(actualizarDashboard, 60000);

    function actualizarDashboard() {
        fetch('/admin/dashboard', {
            headers: {
                'X-Requested-With': 'XMLHttpRequest'
            }
        })
                .then(response => response.text())
                .then(html => {
                    // Aquí podrías actualizar solo las partes necesarias del DOM
                    // Por simplicidad, recargamos la página
                    location.reload();
                })
                .catch(error => console.error('Error al actualizar dashboard:', error));
    }
});
