
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
</head>
<body>
    <div class="bg-white rounded-lg shadow p-6">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">Registro de Notas Prueba</h2>
        <table class="min-w-full divide-y divide-gray-200">
            <thead class="bg-blue-100">
                <tr>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Estudiante</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Evaluación</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Nota</th>
                    <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase">Acción</th>
                </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
                <tr>
                    <td class="px-6 py-4">Juan Pérez</td>
                    <td class="px-6 py-4">Parcial 1</td>
                    <td class="px-6 py-4">18.5</td>
                    <td class="px-6 py-4"><button class="text-blue-600 hover:underline">Editar</button></td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
