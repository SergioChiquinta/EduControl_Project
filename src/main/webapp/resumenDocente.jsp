
<%@ page contentType="text/html; charset=UTF-8" %>
<div class="bg-white rounded-lg shadow p-6">
    <h2 class="text-2xl font-bold text-gray-800 mb-6">Panel del Docente</h2>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
        <!-- Asignaturas Asignadas -->
        <div class="bg-blue-50 rounded-lg p-6 border border-blue-100">
            <div class="flex items-center justify-between">
                <div>
                    <p class="text-gray-600">Asignaturas Asignadas</p>
                    <h3 class="text-3xl font-bold text-blue-800">
                        <%= request.getAttribute("asignaturasAsignadas") != null ? request.getAttribute("asignaturasAsignadas") : "0" %>
                    </h3>
                </div>
                <div class="bg-blue-100 p-3 rounded-full">
                    <i class="fas fa-book text-blue-600 text-xl"></i>
                </div>
            </div>
        </div>

        <!-- Cursos Activos -->
        <div class="bg-green-50 rounded-lg p-6 border border-green-100">
            <div class="flex items-center justify-between">
                <div>
                    <p class="text-gray-600">Cursos Activos</p>
                    <h3 class="text-3xl font-bold text-green-800">
                        <%= request.getAttribute("cursosActivos") != null ? request.getAttribute("cursosActivos") : "0" %>
                    </h3>
                </div>
                <div class="bg-green-100 p-3 rounded-full">
                    <i class="fas fa-users-class text-green-600 text-xl"></i>
                </div>
            </div>
        </div>
    </div>
</div>
