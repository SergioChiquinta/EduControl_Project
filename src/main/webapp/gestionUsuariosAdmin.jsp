
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="css/gestionUsuariosAdmin.css">

<div class="container mx-auto p-6 bg-white shadow rounded-lg">
    <h2 class="text-2xl font-bold mb-6">Gestión de Usuarios</h2>

    <!-- Filtro -->
    <form id="formFiltro" class="flex flex-wrap gap-2 mb-4">
        <select id="campoFiltro" name="campo" class="border p-2 rounded">
            <option value="">Todos</option>
            <option value="nombre">Nombre</option>
            <option value="correo">Correo</option>
            <option value="rol">Rol</option>
        </select>
        <input type="text" id="valorFiltro" name="valor" placeholder="Buscar..." class="border p-2 rounded flex-grow">
        <button type="submit" class="bg-blue-600 text-white px-4 rounded hover:bg-blue-700">Filtrar</button>
    </form>

    <!-- Botón Crear -->
    <button id="btnMostrarForm" class="bg-green-600 text-white px-4 py-2 rounded mb-4 hover:bg-green-700">+ Nuevo Usuario</button>

    <!-- Formulario Crear -->
    <form id="formCrear" style="display: none;" class="space-y-4 border p-4 rounded">
        <div>
            <label>Nombre</label>
            <input type="text" name="nombre" id="nombreNuevo" required class="w-full border rounded p-2">
        </div>
        <div>
            <label>Correo</label>
            <input type="email" name="correo" id="correoNuevo" required class="w-full border rounded p-2">
        </div>
        <div>
            <label>Contraseña</label>
            <input type="password" name="contrasena" required class="w-full border rounded p-2">
        </div>
        <div>
            <label>Rol</label>
            <select name="rol" id="rolCrear" required class="w-full border rounded p-2">
                <option value="administrador">Administrador</option>
                <option value="docente">Docente</option>
                <option value="estudiante">Estudiante</option>
            </select>
        </div>
        <div id="divSalon" style="display: none;">
            <label>Salón</label>
            <select name="salonId" class="w-full border rounded p-2">
                <c:forEach var="s" items="${listaSalones}">
                    <option value="${s[0]}">${s[1]}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Guardar Usuario</button>
    </form>

    <!-- Tabla Usuarios -->
    <div class="overflow-x-auto mt-6">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-50">
                <tr>
                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">ID</th>
                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">Nombre</th>
                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">Correo</th>
                    <th class="px-4 py-2 text-left text-xs font-medium text-gray-500 uppercase">Rol</th>
                    <th class="px-4 py-2 text-center text-xs font-medium text-gray-500 uppercase">Acciones</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="u" items="${listaUsuarios}">
                    <tr>
                        <td class="px-4 py-2">${u.id}</td>
                        <td class="px-4 py-2">${u.username}</td>
                        <td class="px-4 py-2">${u.correo}</td>
                        <td class="px-4 py-2">${u.rol}</td>
                        <td class="px-4 py-2 text-center flex flex-wrap justify-center gap-2">
                            <c:if test="${u.rol != 'administrador'}">
                                <button class="btn-accion btnEditar bg-yellow-500 text-white px-3 py-1 rounded hover:bg-yellow-600" 
                                    data-id="${u.id}" data-nombre="${u.username}" data-correo="${u.correo}">Editar</button>
                                <button class="btn-accion btnEliminar bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700" 
                                    data-id="${u.id}">Eliminar</button>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Edición -->
<div id="modalEditar" style="display:none;" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <form id="formEditar" class="bg-white p-6 rounded shadow space-y-4 w-96">
        <input type="hidden" name="id" id="editarId">
        <div>
            <label>Nombre</label>
            <input type="text" name="nombre" id="editarNombre" required class="w-full border rounded p-2">
        </div>
        <div>
            <label>Correo</label>
            <input type="email" name="correo" id="editarCorreo" required class="w-full border rounded p-2">
        </div>
        <button type="submit" class="bg-yellow-500 text-white px-4 py-2 rounded hover:bg-yellow-600">Guardar Cambios</button>
        <button type="button" id="btnCerrarModal" class="ml-2 bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500">Cancelar</button>
    </form>
</div>

<!-- Modal Confirmar Eliminar -->
<div id="modalConfirmarEliminar" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 hidden">
    <div class="bg-white p-6 rounded shadow-md text-center">
        <h2 class="text-lg font-bold mb-4">¿Estás seguro?</h2>
        <p>Esta acción eliminará al usuario permanentemente.</p>
        <div class="mt-4 flex justify-center space-x-4">
            <button id="btnCancelarEliminar" class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">Cancelar</button>
            <button id="btnConfirmarEliminar" class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700" data-id="">Eliminar</button>
        </div>
    </div>
</div>
