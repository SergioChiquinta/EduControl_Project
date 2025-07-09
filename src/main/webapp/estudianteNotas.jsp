
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="Model.AsignaturaNota"%>
<%@page import="Model.EvaluacionNota"%>
<%@page import="Model.Usuario"%>

<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    List<AsignaturaNota> listaNotas = (List<AsignaturaNota>) request.getAttribute("listaNotas");
%>

<style>
    .arrow {
        display: inline-block;
        transition: transform 0.3s ease;
        border: solid white;
        border-width: 0 2px 2px 0;
        padding: 5px;
        transform: rotate(45deg); /* ► inicial */
    }
    .rotate-down {
        transform: rotate(135deg); /* ▼ abierto */
    }
</style>

<div class="max-w-5xl mx-auto">
    <h1 class="text-2xl font-bold mb-6 text-gray-800">Mis Notas</h1>

    <% if (listaNotas != null && !listaNotas.isEmpty()) { %>
    <% for (AsignaturaNota asignatura : listaNotas) { %>
    <div class="mb-4 border rounded shadow">
        <button 
            class="w-full flex justify-between items-center px-4 py-3 bg-[#03397b] text-white font-semibold hover:bg-blue-800 focus:outline-none"
            onclick="togglePanel('<%=asignatura.getId()%>')">
            <span><%= asignatura.getNombreMateria() %></span>
            <span id="arrow-<%=asignatura.getId()%>" class="arrow"></span>
        </button>
        <div id="panel-<%=asignatura.getId()%>" class="hidden px-4 py-3 bg-gray-50">
            <table class="min-w-full bg-white border rounded">
                <thead class="bg-gray-200">
                    <tr>
                        <th class="py-2 px-4 text-left">Evaluación</th>
                        <th class="py-2 px-4 text-center">Peso (%)</th>
                        <th class="py-2 px-4 text-center">Nota</th>
                        <th class="py-2 px-4 text-center">Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (EvaluacionNota eval : asignatura.getEvaluaciones()) { %>
                    <tr class="border-b hover:bg-gray-100">
                        <td class="py-2 px-4"><%= eval.getNombreEvaluacion() %></td>
                        <td class="py-2 px-4 text-center"><%= eval.getPeso() %></td>
                        <td class="py-2 px-4 text-center">
                            <%= (eval.getNota() == 0.0 ? "Por calificar" : eval.getNota()) %>
                        </td>
                        <td class="py-2 px-4 text-center <%= eval.getEstadoColor() %> font-semibold"><%= eval.getEstado() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
            <div class="mt-4 pr-4 text-right font-medium">
                Promedio materia: <span class="text-blue-700"><%= asignatura.getPromedioMateria() %></span> 
                - Estado general: <span class="text-gray-800 font-bold"><%= asignatura.getEstadoGeneral() %></span>
            </div>
        </div>
    </div>
    <% } %>
    <% } else { %>
    <p>No hay notas registradas.</p>
    <% } %>
</div>
