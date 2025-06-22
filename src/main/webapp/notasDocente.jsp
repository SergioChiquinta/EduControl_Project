
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario"%>
<%@page import="Model.Evaluacion"%>
<%@page import="Model.EstudianteNotaDTO"%>
<%@page import="java.util.List"%>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !u.getRol().equalsIgnoreCase("docente")) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<String> salones = (List<String>) request.getAttribute("salones");
    List<Evaluacion> evaluaciones = (List<Evaluacion>) request.getAttribute("evaluaciones");
    List<EstudianteNotaDTO> estudiantes = (List<EstudianteNotaDTO>) request.getAttribute("estudiantes");
    String salonSeleccionado = (String) request.getAttribute("salonSeleccionado");
    Integer evaluacionSeleccionada = (Integer) request.getAttribute("evaluacionSeleccionada");
    
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Asignación de Notas</title>
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">
</head>
<body class="bg-gray-100">
    <div class="container mx-auto p-6">
        <h2 class="text-2xl font-bold text-gray-800 mb-6">Asignación de Notas</h2>
        
        <% if (mensaje != null) { %>
            <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
                <%= mensaje %>
            </div>
        <% } %>
        
        <% if (error != null) { %>
            <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                <%= error %>
            </div>
        <% } %>
        
        <div class="bg-white p-6 rounded-lg shadow-md mb-6">
            <form method="GET" action="AsignarNotaController" class="flex flex-wrap gap-4">
                <div class="w-full md:w-1/3">
                    <label for="salon" class="block text-sm font-medium text-gray-700 mb-1">Salón:</label>
                    <select id="salon" name="salon" class="w-full p-2 border rounded" required>
                        <option value="">Seleccione un salón</option>
                        <% if (salones != null) { 
                            for (String salon : salones) { %>
                                <option value="<%= salon %>" <%= salon.equals(salonSeleccionado) ? "selected" : "" %>>
                                    <%= salon %>
                                </option>
                        <%    }
                           } %>
                    </select>
                </div>
                
                <div class="w-full md:w-1/3">
                    <label for="evaluacion" class="block text-sm font-medium text-gray-700 mb-1">Evaluación:</label>
                    <select id="evaluacion" name="evaluacion" class="w-full p-2 border rounded" required>
                        <option value="">Seleccione una evaluación</option>
                        <% if (evaluaciones != null) { 
                            for (Evaluacion eval : evaluaciones) { %>
                                <option value="<%= eval.getId() %>" 
                                    <%= (evaluacionSeleccionada != null && eval.getId() == evaluacionSeleccionada) ? "selected" : "" %>>
                                    <%= eval.getNombre() %>
                                </option>
                        <%    }
                           } %>
                    </select>
                </div>
                
                <div class="w-full md:w-1/6 flex items-end">
                    <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                        Filtrar
                    </button>
                </div>
            </form>
        </div>
        
        <% if (estudiantes != null && !estudiantes.isEmpty()) { %>
            <div class="bg-white p-6 rounded-lg shadow-md">
                <h2 class="text-xl font-semibold text-gray-800  mb-4">Estudiantes del salón <%= salonSeleccionado %></h2>
                
                <form method="POST" action="AsignarNotaController" class="mb-6">
                    <input type="hidden" name="salonSeleccionado" value="<%= salonSeleccionado %>">
                    <input type="hidden" name="evaluacionSeleccionada" value="<%= evaluacionSeleccionada %>">
                    
                    <div class="overflow-x-auto">
                        <table class="min-w-full bg-white">
                            <thead>
                                <tr>
                                    <th class="py-2 px-4 border-b border-gray-200 bg-gray-50 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Estudiante</th>
                                    <th class="py-2 px-4 border-b border-gray-200 bg-gray-50 text-left text-xs font-semibold text-gray-600 uppercase tracking-wider">Nota</th>
                                    <th class="py-2 px-4 border-b border-gray-200 bg-gray-50"></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (EstudianteNotaDTO estudiante : estudiantes) { %>
                                    <tr>
                                        <td class="py-2 px-4 border-b border-gray-200">
                                            <%= estudiante.getNombreEstudiante() %>
                                        </td>
                                        <td class="py-2 px-4 border-b border-gray-200">
                                            <%
                                                String notaFormateada = "";
                                                if (estudiante.getNota() != null) {
                                                    notaFormateada = String.format(java.util.Locale.US, "%.2f", estudiante.getNota());
                                                }
                                            %>
                                            <input type="number" step="0.01" lang="en" inputmode="decimal" name="nota[]" value="<%= notaFormateada %>"
                                                   min="0" max="20" class="p-1 border rounded w-20">
                                        </td>
                                        <td class="py-2 px-4 border-b border-gray-200">
                                            <input type="hidden" name="estudianteId[]" value="<%= estudiante.getEstudianteId() %>">
                                            <input type="hidden" name="evaluacionId" value="<%= evaluacionSeleccionada %>">
                                            <button type="submit" class="bg-green-500 hover:bg-green-700 text-white font-bold py-1 px-3 rounded text-sm">
                                                Guardar
                                            </button>
                                        </td>
                                    </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        <% } else if (salonSeleccionado != null && evaluacionSeleccionada != null) { %>
            <div class="bg-white p-6 rounded-lg shadow-md">
                <p class="text-gray-600">No se encontraron estudiantes para el salón seleccionado.</p>
            </div>
        <% } %>
    </div>
</body>
</html>