
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.ResumenAdmin, Model.LogActividad, java.util.List, java.util.ArrayList" %>
<%
    ResumenAdmin resumen = (ResumenAdmin) request.getAttribute("resumen");
    List<LogActividad> logs = (List<LogActividad>) request.getAttribute("logs");
    
    if (resumen == null) {
        resumen = new ResumenAdmin();
        resumen.setTotalUsuarios(0);
        resumen.setTotalDocentes(0);
        resumen.setTotalEstudiantes(0);
    }
    
    if (logs == null) {
        logs = new ArrayList<>();
    }
%>
<div class="container mx-auto px-4 py-8">
    <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">Resumen del Sistema</h2>

        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
            <!-- Tarjeta 1 -->
            <div class="bg-blue-50 rounded-lg p-6 border border-blue-100">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-600">Usuarios Registrados</p>
                        <h3 class="text-3xl font-bold text-blue-800">
                            <%= (resumen.getTotalUsuarios() == 0) ? "..." : resumen.getTotalUsuarios() %>
                        </h3>
                    </div>
                    <div class="bg-blue-100 p-3 rounded-full">
                        <i class="fas fa-users text-blue-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Tarjeta 2 -->
            <div class="bg-green-50 rounded-lg p-6 border border-green-100">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-600">Docentes Activos</p>
                        <h3 class="text-3xl font-bold text-green-800">
                            <%= (resumen.getTotalDocentes() == 0) ? "..." : resumen.getTotalDocentes() %>
                        </h3>
                    </div>
                    <div class="bg-green-100 p-3 rounded-full">
                        <i class="fas fa-chalkboard-teacher text-green-600 text-xl"></i>
                    </div>
                </div>
            </div>

            <!-- Tarjeta 3 -->
            <div class="bg-purple-50 rounded-lg p-6 border border-purple-100">
                <div class="flex items-center justify-between">
                    <div>
                        <p class="text-gray-600">Estudiantes Activos</p>
                        <h3 class="text-3xl font-bold text-purple-800">
                            <%= (resumen.getTotalEstudiantes() == 0) ? "..." : resumen.getTotalEstudiantes() %>
                        </h3>
                    </div>
                    <div class="bg-purple-100 p-3 rounded-full">
                        <i class="fas fa-user-graduate text-purple-600 text-xl"></i>
                    </div>
                </div>
            </div>
        </div>

        <!-- Sección de actividad reciente -->
        <div class="mt-8">
            <h3 class="text-xl font-semibold text-gray-700 mb-4">Actividad Reciente</h3>
            <div class="bg-white border border-gray-200 rounded-lg overflow-hidden">
                <table class="min-w-full divide-y divide-gray-200">
                    <thead class="bg-gray-50">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Evento</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Usuario</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fecha</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200">
                        <% for (LogActividad log : logs) { %>
                        <tr>
                            <td class="px-6 py-4 whitespace-nowrap"><%= log.getAccion() %></td>
                            <td class="px-6 py-4 whitespace-nowrap"><%= log.getUsuario() %> (<%= log.getCorreo() %>)</td>
                            <td class="px-6 py-4 whitespace-nowrap"><%= log.getFechaRegistro() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
