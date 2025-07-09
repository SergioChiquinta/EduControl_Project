
<%@page import="Model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="max-w-4xl mx-auto">
    <h1 class="text-2xl font-bold mb-4 text-gray-800">Mis Notas</h1>

    <table class="min-w-full bg-white rounded shadow overflow-hidden">
        <thead class="bg-blue-700 text-white">
            <tr>
                <th class="py-3 px-6 text-left">Asignatura</th>
                <th class="py-3 px-6 text-left">Evaluación</th>
                <th class="py-3 px-6 text-center">Nota</th>
                <th class="py-3 px-6 text-center">Estado</th>
            </tr>
        </thead>
        <tbody>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-3 px-6">Matemáticas Avanzadas</td>
                <td class="py-3 px-6">Parcial 1</td>
                <td class="py-3 px-6 text-center">15</td>
                <td class="py-3 px-6 text-center text-green-600 font-semibold">Aprobado</td>
            </tr>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-3 px-6">Historia Universal</td>
                <td class="py-3 px-6">Ensayo</td>
                <td class="py-3 px-6 text-center">18</td>
                <td class="py-3 px-6 text-center text-green-600 font-semibold">Aprobado</td>
            </tr>
            <tr class="border-b hover:bg-gray-50">
                <td class="py-3 px-6">Física Experimental</td>
                <td class="py-3 px-6">Laboratorio</td>
                <td class="py-3 px-6 text-center">17</td>
                <td class="py-3 px-6 text-center text-yellow-600 font-semibold">En Progreso</td>
            </tr>
        </tbody>
    </table>
</div>
