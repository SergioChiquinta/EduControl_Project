
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="p-6">
  <div class="flex items-center justify-between mb-4">
    <h1 class="text-2xl font-bold text-gray-800">Gestión de Notas</h1>
    <button class="bg-green-500 text-white px-4 py-2 rounded hover:bg-green-600">+ Nueva Nota</button>
  </div>

  <div class="overflow-x-auto bg-white shadow rounded-lg">
    <table class="min-w-full divide-y divide-gray-200">
      <thead class="bg-gray-100">
        <tr>
          <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase tracking-wider">ID Nota</th>
          <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase tracking-wider">Estudiante</th>
          <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase tracking-wider">Evaluación</th>
          <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase tracking-wider">Nota</th>
          <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase tracking-wider">Periodo Académico</th>
          <th class="px-6 py-3 text-left text-xs font-semibold text-gray-700 uppercase tracking-wider">Materia</th>
          <th class="px-6 py-3 text-center text-xs font-semibold text-gray-700 uppercase tracking-wider">Acciones</th>
        </tr>
      </thead>
      <tbody class="bg-white divide-y divide-gray-200">
        <tr>
          <td class="px-6 py-4 text-sm text-gray-900">N001</td>
          <td class="px-6 py-4 text-sm text-gray-900">María Torres</td>
          <td class="px-6 py-4 text-sm text-gray-900">Examen Final</td>
          <td class="px-6 py-4 text-sm text-gray-900">18.5</td>
          <td class="px-6 py-4 text-sm text-gray-900">2025-I</td>
          <td class="px-6 py-4 text-sm text-gray-900">Matemática Básica</td>
          <td class="px-6 py-4 text-center">
            <button class="bg-blue-500 text-white px-3 py-1 text-sm rounded hover:bg-blue-600 mr-2">Editar</button>
            <button class="bg-red-500 text-white px-3 py-1 text-sm rounded hover:bg-red-600">Eliminar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</div>
