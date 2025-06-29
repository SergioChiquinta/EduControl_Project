
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="container mx-auto p-6 bg-white shadow-md rounded-lg">
    <h2 class="text-2xl font-bold mb-4">Configuración de Usuario</h2>

    <div id="mensaje" class="mb-4 hidden p-3 rounded"></div>

    <form id="formConfiguracion" method="post" action="ConfiguracionController">
        <div>
            <label for="nombre" class="block text-sm font-medium text-gray-700">Nombre</label>
            <input type="text" id="nombre" name="nombre" value="${usuario.username}" disabled required class="w-full border rounded p-2">
        </div>
        <br>
        <div>
            <label for="correo" class="block text-sm font-medium text-gray-700">Correo</label>
            <input type="email" id="correo" name="correo" value="${usuario.correo}" disabled required class="w-full border rounded p-2">
        </div>
        <br>
        <div>
            <label for="rol" class="block text-sm font-medium text-gray-700">Rol</label>
            <input type="text" id="rol" name="rol" value="${usuario.rol}" readonly class="w-full border rounded p-2 bg-gray-100">
        </div>
        <br>
        <div>
            <label for="contrasena" class="block text-sm font-medium text-gray-700">Contraseña (dejar vacío si no se cambia)</label>
            <input type="password" id="contrasena" name="contrasena" disabled class="w-full border rounded p-2">
        </div>
        <br>
        <button type="button" id="btnEditar" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Editar</button>
        <button id="btnGuardar" type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-medium py-2 px-4 rounded" 
                style="display: none;">Guardar</button>
    </form>
</div>
