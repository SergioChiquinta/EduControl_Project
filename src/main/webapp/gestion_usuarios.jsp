
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario" %>
<%@page import="java.util.List" %>
<%
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    String filtro = (String) request.getAttribute("filtro");
    String valor = (String) request.getAttribute("valor");
    String error = (String) request.getAttribute("error");
    String exito = (String) request.getAttribute("exito");
%>

<div class="bg-white rounded-lg shadow p-6 mx-4">
    <h1 class="text-2xl font-bold text-gray-800 mb-6">Gestión de Usuarios</h1>
    
    <% if (error != null) { %>
    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
        <%= error %>
    </div>
    <% } %>
    
    <% if (exito != null) { %>
    <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded mb-4">
        <%= exito %>
    </div>
    <% } %>
    
    <!-- Filtros de búsqueda -->
    <div class="bg-gray-50 rounded-lg border border-gray-200 mb-6">
        <div class="px-4 py-3 border-b border-gray-200">
            <h2 class="text-lg font-medium text-gray-700">Filtrar Usuarios</h2>
        </div>
        <div class="p-4">
            <form method="get" action="GestionUsuariosController" 
                onsubmit="buscarUsuarios(this); return false;" 
                class="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                    <label for="filtro" class="block text-sm font-medium text-gray-700 mb-1">Filtrar por:</label>
                    <select id="filtro" name="filtro" class="w-full rounded-md border-gray-300 shadow-sm">
                        <option value="">Todos</option>
                        <option value="id" <%= "id".equals(filtro) ? "selected" : "" %>>ID</option>
                        <option value="nombre" <%= "nombre".equals(filtro) ? "selected" : "" %>>Nombre</option>
                        <option value="correo" <%= "correo".equals(filtro) ? "selected" : "" %>>Correo</option>
                        <option value="rol" <%= "rol".equals(filtro) ? "selected" : "" %>>Rol</option>
                    </select>
                </div>
                <div>
                    <label for="valor" class="block text-sm font-medium text-gray-700 mb-1">Valor:</label>
                    <input type="text" id="valor" name="valor" class="w-full rounded-md border-gray-300 shadow-sm" 
                           value="<%= valor != null ? valor : "" %>">
                </div>
                <div class="flex items-end space-x-2">
                    <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded-md hover:bg-blue-700">
                        <i class="fas fa-search mr-2"></i> Buscar
                    </button>
                    <a href="GestionUsuariosController" class="bg-gray-200 text-gray-700 px-4 py-2 rounded-md hover:bg-gray-300">
                        <i class="fas fa-sync-alt"></i>
                    </a>
                </div>
            </form>
        </div>
    </div>
    
    <!-- Tabla de usuarios -->
    <div class="bg-gray-50 rounded-lg border border-gray-200">
        <div class="px-4 py-3 border-b border-gray-200 flex justify-between items-center">
            <h2 class="text-lg font-medium text-gray-700">Lista de Usuarios</h2>
            <button type="button" onclick="abrirModalNuevo()" class="bg-green-600 text-white px-4 py-2 rounded-md hover:bg-green-700">
                <i class="fas fa-plus mr-2"></i> Nuevo Usuario
            </button>
        </div>
        <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
                <thead class="bg-gray-100">
                    <tr>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">ID</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nombre</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Correo</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Rol</th>
                        <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                    </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                    <% if (usuarios != null && !usuarios.isEmpty()) { %>
                        <% for (Usuario u : usuarios) { %>
                        <tr>
                            <td class="px-6 py-4 whitespace-nowrap"><%= u.getId() %></td>
                            <td class="px-6 py-4 whitespace-nowrap"><%= u.getUsername() %></td>
                            <td class="px-6 py-4 whitespace-nowrap"><%= u.getCorreo() %></td>
                            <td class="px-6 py-4 whitespace-nowrap">
                                <span class="px-2 py-1 text-xs font-semibold rounded-full 
                                    <%= "administrador".equals(u.getRol()) ? "bg-blue-100 text-blue-800" : 
                                       "docente".equals(u.getRol()) ? "bg-green-100 text-green-800" : "bg-purple-100 text-purple-800" %>">
                                    <%= u.getRol() %>
                                </span>
                            </td>
                            <td class="px-6 py-4 whitespace-nowrap">
                                <button type="button" class="text-yellow-600 hover:text-yellow-900 mr-3"
                                        onclick="abrirModalEditar(<%= u.getId() %>, '<%= u.getUsername() %>', '<%= u.getCorreo() %>', '<%= u.getRol() %>')">
                                    <i class="fas fa-edit"></i>
                                </button>
                                <button type="button" class="text-red-600 hover:text-red-900"
                                        onclick="confirmarEliminacion(<%= u.getId() %>)">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </td>
                        </tr>
                        <% } %>
                    <% } else { %>
                        <tr>
                            <td colspan="5" class="px-6 py-4 text-center text-gray-500">
                                No se encontraron usuarios
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Modal Crear/Editar Usuario -->
<div id="usuarioModal" class="fixed inset-0 z-50 hidden overflow-y-auto bg-black bg-opacity-50">
  <div class="flex items-center justify-center min-h-screen px-4">
    <div class="bg-white rounded-lg shadow-xl w-full max-w-lg">
      <form id="usuarioForm" method="post" enctype="multipart/form-data" class="p-6 space-y-4" onsubmit="guardarUsuario(event)">
        <input type="hidden" name="action" value="" id="modalAction">
        <input type="hidden" name="id" id="usuarioId">

        <h2 class="text-xl font-semibold text-gray-800" id="modalTitle">Nuevo Usuario</h2>

        <div>
          <label for="nombre" class="block text-sm font-medium text-gray-700">Nombre:</label>
          <input type="text" name="nombre" id="nombre" class="mt-1 w-full rounded-md border-gray-300 shadow-sm" required>
        </div>
        <div>
          <label for="correo" class="block text-sm font-medium text-gray-700">Correo:</label>
          <input type="email" name="correo" id="correo" class="mt-1 w-full rounded-md border-gray-300 shadow-sm" required>
        </div>
        <!-- Cambia el formulario en el modal para que la contraseña sea opcional -->
        <div>
            <label for="contraseña" class="block text-sm font-medium text-gray-700">Contraseña (dejar en blanco para no cambiar):</label>
            <input type="password" name="contraseña" id="contraseña" class="mt-1 w-full rounded-md border-gray-300 shadow-sm">
        </div>
        <div>
          <label for="rol" class="block text-sm font-medium text-gray-700">Rol:</label>
          <select name="rol" id="rol" class="mt-1 w-full rounded-md border-gray-300 shadow-sm" required>
            <option value="administrador">Administrador</option>
            <option value="docente">Docente</option>
            <option value="estudiante">Estudiante</option>
          </select>
        </div>

        <div class="flex justify-end space-x-2 pt-4">
          <button type="button" onclick="cerrarModal()" class="bg-gray-200 text-gray-800 px-4 py-2 rounded hover:bg-gray-300">
            Cancelar
          </button>
          <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">
            Guardar
          </button>
        </div>
      </form>
    </div>
  </div>
</div>

<!-- Modal de confirmación -->
<div id="confirmarModal" class="fixed inset-0 z-50 hidden overflow-y-auto bg-black bg-opacity-50">
  <div class="flex items-center justify-center min-h-screen px-4">
    <div class="bg-white rounded-lg shadow-xl w-full max-w-md">
      <div class="p-6">
        <h3 class="text-lg font-medium text-gray-900">Confirmar eliminación</h3>
        <div class="mt-2">
          <p class="text-sm text-gray-500">¿Estás seguro de que deseas eliminar este usuario?</p>
        </div>
        <div class="mt-4 flex justify-end space-x-3">
          <button type="button" onclick="cerrarConfirmacion()"
                  class="px-4 py-2 bg-gray-200 text-gray-800 rounded hover:bg-gray-300">
            Cancelar
          </button>
          <button type="button" id="confirmarEliminarBtn"
                  class="px-4 py-2 bg-red-600 text-white rounded hover:bg-red-700">
            Eliminar
          </button>
        </div>
      </div>
    </div>
  </div>
</div>

<script>

</script>
