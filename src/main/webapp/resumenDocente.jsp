
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
    </head>
    <body>
        <div class="bg-white rounded-lg shadow p-6">
            <h2 class="text-2xl font-bold text-gray-800 mb-6">Panel del Docente</h2>

            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div class="bg-blue-50 rounded-lg p-6 border border-blue-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-gray-600">Asignaturas Asignadas</p>
                            <h3 class="text-3xl font-bold text-blue-800">3</h3>
                        </div>
                        <div class="bg-blue-100 p-3 rounded-full">
                            <i class="fas fa-book text-blue-600 text-xl"></i>
                        </div>
                    </div>
                </div>

                <div class="bg-green-50 rounded-lg p-6 border border-green-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-gray-600">Cursos Activos</p>
                            <h3 class="text-3xl font-bold text-green-800">5</h3>
                        </div>
                        <div class="bg-green-100 p-3 rounded-full">
                            <i class="fas fa-users-class text-green-600 text-xl"></i>
                        </div>
                    </div>
                </div>

                <div class="bg-yellow-50 rounded-lg p-6 border border-yellow-100">
                    <div class="flex items-center justify-between">
                        <div>
                            <p class="text-gray-600">Evaluaciones Programadas</p>
                            <h3 class="text-3xl font-bold text-yellow-800">4</h3>
                        </div>
                        <div class="bg-yellow-100 p-3 rounded-full">
                            <i class="fas fa-tasks text-yellow-600 text-xl"></i>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
