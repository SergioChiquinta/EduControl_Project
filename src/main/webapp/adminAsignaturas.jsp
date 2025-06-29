
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="p-6 bg-white shadow rounded-lg">
    <div class="flex items-center justify-between mb-4">
        <h1 class="text-2xl font-bold text-gray-800">Gestión de Asignaturas</h1>
        <button id="btnNuevaAsignatura" class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">+ Nueva Asignatura</button>
    </div>

    <!-- Tabla -->
    <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">ID</th>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">Nombre</th>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">Docente Asignado</th>
                    <th class="px-6 py-3 text-center text-xs font-semibold text-gray-700 uppercase">Acciones</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="a" items="${listaAsignaturas}">
                    <tr>
                        <td class="px-6 py-4">${a.id}</td>
                        <td class="px-6 py-4">${a.nombre}</td>
                        <td class="px-6 py-4">${a.docenteNombre}</td>
                        <td class="px-6 py-4 text-center space-x-2">
                            <button class="btn-accion btnEditar bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                                    data-id="${a.id}"
                                    data-nombre="${a.nombre}"
                                    data-docenteid="${a.docenteId}">Editar</button>
                            <button class="btn-accion btnEliminar bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                                    data-id="${a.id}">Eliminar</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Crear/Editar -->
<div id="modalAsignatura" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 hidden">
    <form id="formAsignatura" class="bg-white p-6 rounded shadow space-y-4 w-96">
        <input type="hidden" name="id" id="asigId">
        <div>
            <label>Nombre de Asignatura</label>
            <input type="text" name="nombre" id="asigNombre" required class="w-full border rounded p-2">
        </div>
        <div>
            <label>Docente</label>
            <select name="docenteId" id="asigDocente" required class="w-full border rounded p-2">
                <c:forEach var="d" items="${listaDocentes}">
                    <option value="${d[0]}">${d[1]}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Guardar</button>
        <button type="button" id="btnCerrarModal" class="ml-2 bg-gray-400 text-white px-4 py-2 rounded hover:bg-gray-500">Cancelar</button>
    </form>
</div>

<!-- Modal Confirmar Eliminar -->
<div id="modalConfirmarEliminar" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 hidden">
    <div class="bg-white p-6 rounded shadow text-center">
        <h2 class="text-lg font-bold mb-4">¿Estás seguro?</h2>
        <p>Esta acción eliminará la asignatura permanentemente.</p>
        <div class="mt-4 flex justify-center space-x-4">
            <button id="btnCancelarEliminar" class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">Cancelar</button>
            <button id="btnConfirmarEliminar" class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700">Eliminar</button>
        </div>
    </div>
</div>
