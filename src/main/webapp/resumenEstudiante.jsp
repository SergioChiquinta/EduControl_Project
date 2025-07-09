
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*" %>
<div class="bg-white rounded-lg shadow p-6 max-w-4xl mx-auto mt-2">
    <h2 class="text-2xl font-bold text-gray-800 mb-6">Panel del Estudiante</h2>

    <div class="mb-4">
        <h3 class="text-lg font-bold text-gray-700">Salón Actual</h3>
        <p class="text-gray-800">
            <%= request.getAttribute("salonActual") != null ? request.getAttribute("salonActual") : "No asignado" %>
        </p>
    </div>

    <div class="mt-6">
        <h3 class="text-lg font-bold text-gray-700 mb-2">Evaluaciones Programadas</h3>
        <div class="overflow-x-auto">
            <table class="min-w-full bg-white border border-gray-200">
                <thead>
                    <tr class="bg-[#03397b] text-white">
                        <th class="py-3 px-4 text-left font-semibold">Asignatura</th>
                        <th class="py-3 px-4 text-left font-semibold">Evaluaciones</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        Map<String, List<String>> evaluacionesAgrupadas = (Map<String, List<String>>) request.getAttribute("evaluacionesAgrupadas");
                        if (evaluacionesAgrupadas != null && !evaluacionesAgrupadas.isEmpty()) {
                            for (Map.Entry<String, List<String>> entry : evaluacionesAgrupadas.entrySet()) {
                                String materia = entry.getKey();
                                List<String> evals = entry.getValue();
                                String evalsStr = String.join(", ", evals);
                    %>
                    <tr class="border-b hover:bg-gray-100">
                        <td class="py-3 px-4"><%= materia %></td>
                        <td class="py-3 px-4"><%= evalsStr %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="2" class="py-3 px-4 text-center text-gray-500">No hay evaluaciones programadas.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>