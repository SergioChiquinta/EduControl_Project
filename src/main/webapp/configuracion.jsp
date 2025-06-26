
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="p-6 max-w-3xl mx-auto bg-white shadow rounded-lg">
    <h1 class="text-2xl font-bold mb-6 text-gray-800">Configuración</h1>

    <form action="#" method="POST" class="space-y-6" id="configForm">
        <div>
            <label class="block mb-1 font-semibold text-gray-700" for="nombre">Nombre</label>
            <input id="nombre" name="nombre" type="text" placeholder="Nombre completo"
                   class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                   disabled />
        </div>

        <div>
            <label class="block mb-1 font-semibold text-gray-700" for="email">Correo electrónico</label>
            <input id="email" name="email" type="email" placeholder="email@ejemplo.com"
                   class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                   disabled />
        </div>

        <div>
            <label class="block mb-1 font-semibold text-gray-700" for="password">Cambiar contraseña</label>
            <input id="password" name="password" type="password" placeholder="Nueva contraseña"
                   class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
                   disabled />
        </div>

        <div class="flex space-x-4">
            <button type="button" id="editarBtn" class="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700">Editar</button>
            <button type="submit" id="guardarBtn" class="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700" disabled>Guardar cambios</button>
        </div>
    </form>
</div>

<script>
    const editarBtn = document.getElementById('editarBtn');
    const guardarBtn = document.getElementById('guardarBtn');
    const formElements = document.querySelectorAll('#configForm input');

    editarBtn.addEventListener('click', () => {
        formElements.forEach(input => input.disabled = false);
        guardarBtn.disabled = false;
        editarBtn.disabled = true;
    });
</script>
