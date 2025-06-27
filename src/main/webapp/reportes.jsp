
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container mx-auto p-4 md:p-6">
    <h2 class="text-2xl font-bold text-gray-800 mb-6">Reportes Académicos</h2>

    <!-- Botón Nuevo Reporte -->
    <button class="bg-blue-600 hover:bg-blue-700 text-white font-medium py-2 px-4 rounded mb-4" id="btnNuevoReporte">
        + Nuevo Reporte
    </button>

    <!-- Formulario de nuevo reporte -->
    <div class="bg-white rounded-lg shadow-md mb-6 p-6" id="formNuevoReporte" style="display: none;" data-ajax>
        <form id="formGenerarReporte" method="post" action="ReporteController">
            <h3 class="text-1xl font-bold text-gray-800 mb-6">Generar Nuevo Reporte</h3>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-4">
                <div>
                    <label for="selectPeriodo" class="block text-sm font-medium text-gray-700 mb-1">Período</label>
                    <select class="w-full p-2 border border-gray-300 rounded-md" name="periodoId" id="selectPeriodo" required>
                        <!-- JSTL o AJAX -->
                    </select>
                </div>
                <div>
                    <label for="selectSalon" class="block text-sm font-medium text-gray-700 mb-1">Salón</label>
                    <select class="w-full p-2 border border-gray-300 rounded-md" name="salonId" id="selectSalon" required>
                        <!-- Se llena vía JS -->
                    </select>
                </div>
                <div>
                    <label for="selectEstudiante" class="block text-sm font-medium text-gray-700 mb-1">Estudiante</label>
                    <select class="w-full p-2 border border-gray-300 rounded-md" name="estudianteId" id="selectEstudiante" required>
                        <!-- Se llena vía JS -->
                    </select>
                </div>
            </div>

            <button type="submit" class="bg-green-600 hover:bg-green-700 text-white font-medium py-2 px-4 rounded">
                Generar Reporte
            </button>
        </form>
    </div>

    <!-- Tabla de reportes -->
    <div class="overflow-x-auto bg-white rounded-lg shadow-md">
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-blue-50">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Estudiante</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Salón</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Período</th>
                    <th class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">Promedio</th>
                    <th class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">Estado</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Fecha</th>
                    <th class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <c:forEach var="reporte" items="${listaReportes}">
                    <tr>
                        <td class="px-6 py-4 whitespace-nowrap">${reporte.nombreEstudiante}</td>
                        <td class="px-6 py-4 whitespace-nowrap">${reporte.nombreSalon}</td>
                        <td class="px-6 py-4 whitespace-nowrap">${reporte.nombrePeriodo}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-center">${reporte.promedioGeneral}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-center">
                            <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium
                                  ${reporte.estadoAcademico == 'Aprobado' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}">
                                ${reporte.estadoAcademico}
                            </span>
                        </td>
                        <td class="px-6 py-4 whitespace-nowrap">${reporte.fechaGeneracion}</td>
                        <td class="px-6 py-4 whitespace-nowrap text-center space-x-2">
                            <a href="ReporteController?accion=verPDF&id=${reporte.id}" 
                               class="text-blue-600 hover:text-blue-900 border border-blue-600 hover:bg-blue-50 px-3 py-1 rounded text-sm" 
                               target="_blank">Ver</a>
                            <a href="ReporteController?accion=descargarPDF&id=${reporte.id}" 
                               class="text-gray-600 hover:text-gray-900 border border-gray-600 hover:bg-gray-50 px-3 py-1 rounded text-sm">Descargar</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty listaReportes}">
                    <tr>
                        <td colspan="7" class="px-6 py-4 text-center text-gray-500 italic">No se han generado reportes aún.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

<!-- Al final de reportes.jsp -->
<script>
    if (!window.reportesScriptLoaded) {
        window.reportesScriptLoaded = true;
        const script = document.createElement('script');
        script.src = 'js/reportes.js?t=' + new Date().getTime();
        script.defer = true;
        document.body.appendChild(script);
    }
</script>
