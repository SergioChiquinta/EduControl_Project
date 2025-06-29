
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="p-6 bg-white shadow rounded-lg">
    <div class="flex items-center justify-between mb-4">
        <h1 class="text-2xl font-bold text-gray-800">Gestión de Cursos</h1>
        <button id="btnNuevoCurso" class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">+ Nuevo Curso</button>
    </div>

    <!-- Formulario Crear -->
    <form id="formCrearCurso" style="display:none;" class="space-y-4 border p-4 rounded mb-6">
        <div>
            <label>Periodo Académico</label>
            <select name="periodoId" id="periodoId" required class="w-full border rounded p-2">
                <c:forEach var="p" items="${listaPeriodos}">
                    <option value="${p[0]}">${p[1]}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label>Materia</label>
            <select name="materiaId" id="materiaId" required class="w-full border rounded p-2">
                <c:forEach var="m" items="${listaMaterias}">
                    <option value="${m[0]}">${m[1]}</option>
                </c:forEach>
            </select>
        </div>
        <div>
            <label>Salón</label>
            <select name="salonId" id="salonId" required class="w-full border rounded p-2">
                <c:forEach var="s" items="${listaSalones}">
                    <option value="${s[0]}">${s[1]}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">Guardar Curso</button>
    </form>

    <!-- Tabla -->
    <div class="overflow-x-auto">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-gray-100">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">ID</th>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">Materia</th>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">Salón</th>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">Periodo</th>
                    <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase">Docente</th>
                    <th class="px-6 py-3 text-center text-xs font-semibold text-gray-700 uppercase">Acciones</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="c" items="${listaCursos}">
                    <tr>
                        <td class="px-6 py-4">${c.id}</td>
                        <td class="px-6 py-4">${c.nombreMateria}</td>
                        <td class="px-6 py-4">${c.nombreSalon}</td>
                        <td class="px-6 py-4">${c.nombrePeriodo}</td>
                        <td class="px-6 py-4">${c.docenteNombre}</td>
                        <td class="px-6 py-4 text-center">
                            <button class="btnEliminar bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700" data-id="${c.id}">Eliminar</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- Modal Confirmar Eliminar -->
<div id="modalConfirmarEliminar" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 hidden">
    <div class="bg-white p-6 rounded shadow text-center">
        <h2 class="text-lg font-bold mb-4">¿Estás seguro?</h2>
        <p>Esta acción eliminará el curso permanentemente.</p>
        <div class="mt-4 flex justify-center space-x-4">
            <button id="btnCancelarEliminar" class="bg-gray-500 text-white px-4 py-2 rounded hover:bg-gray-600">Cancelar</button>
            <button id="btnConfirmarEliminar" class="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700">Eliminar</button>
        </div>
    </div>
</div>
