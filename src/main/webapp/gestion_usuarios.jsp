
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%
    String exito = (String) request.getAttribute("exito");
    String error = (String) request.getAttribute("error");
%>

<% if (exito != null) { %>
    <div class="bg-green-100 text-green-800 p-4 rounded mb-4"><%= exito %></div>
<% } %>

<% if (error != null) { %>
    <div class="bg-red-100 text-red-800 p-4 rounded mb-4"><%= error %></div>
<% } %>

<%
    List<Usuario> usuarios = new ArrayList<>();
    try {
        List<Usuario> resultado = (List<Usuario>) request.getAttribute("usuarios");
        if (resultado != null) {
            usuarios = resultado;
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    String filtro = (String) request.getAttribute("filtro");
    String valor = (String) request.getAttribute("valor");
%>
<% if (usuarios.isEmpty()) { %>
    <div class="bg-yellow-100 text-yellow-800 p-4 rounded">No se encontraron usuarios o hubo un error al cargarlos.</div>
<% } %>

<div class="max-w-7xl mx-auto mt-6 px-4">
    <h2 class="text-2xl font-bold mb-4">Gestión de Usuarios</h2>

    <!-- Botón para nuevo usuario -->
    <button class="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700 mb-4" data-bs-toggle="modal" data-bs-target="#modalNuevo">Nuevo Usuario</button>

    <!-- Filtro -->
    <form class="flex flex-wrap gap-2 mb-6" method="get" action="GestionUsuariosController">
        <select name="filtro" class="border rounded px-3 py-2">
            <option value="Todos" <%= "Todos".equals(filtro) ? "selected" : "" %>>Todos</option>
            <option value="nombre" <%= "nombre".equals(filtro) ? "selected" : "" %>>Nombre</option>
            <option value="correo" <%= "correo".equals(filtro) ? "selected" : "" %>>Correo</option>
            <option value="rol" <%= "rol".equals(filtro) ? "selected" : "" %>>Rol</option>
        </select>
        <input type="text" name="valor" value="<%= valor != null ? valor : "" %>" class="border rounded px-3 py-2 flex-1" placeholder="Buscar">
        <button type="submit" class="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Filtrar</button>
    </form>

    <!-- Tabla de usuarios -->
    <div class="overflow-x-auto">
        <table class="min-w-full table-auto border rounded-lg">
            <thead class="bg-gray-100">
                <tr>
                    <th class="px-4 py-2 border">ID</th>
                    <th class="px-4 py-2 border">Nombre</th>
                    <th class="px-4 py-2 border">Correo</th>
                    <th class="px-4 py-2 border">Rol</th>
                    <th class="px-4 py-2 border">Acciones</th>
                </tr>
            </thead>
            <tbody>
            <% for (Usuario u : usuarios) { %>
                <tr class="hover:bg-gray-50">
                    <td class="px-4 py-2 border"><%= u.getId() %></td>
                    <td class="px-4 py-2 border"><%= u.getUsername() %></td>
                    <td class="px-4 py-2 border"><%= u.getCorreo() %></td>
                    <td class="px-4 py-2 border"><%= u.getRol() %></td>
                    <td class="px-4 py-2 border space-x-2">
                        <button class="bg-yellow-500 text-white px-3 py-1 rounded text-sm hover:bg-yellow-600" data-bs-toggle="modal" data-bs-target="#modalEditar<%= u.getId() %>">Editar</button>
                        <% if (!"administrador".equals(u.getRol())) { %>
                            <button class="bg-red-600 text-white px-3 py-1 rounded text-sm hover:bg-red-700" type="button" data-bs-toggle="modal" data-bs-target="#modalEliminarUsuario" data-user-id="<%= u.getId() %>">Eliminar</button>
                        <% } %>
                    </td>
                </tr>

                <!-- Modal Editar -->
                <div class="modal fade" id="modalEditar<%= u.getId() %>" tabindex="-1">
                    <div class="modal-dialog">
                        <form action="GestionUsuariosController" method="post" class="modal-content">
                            <input type="hidden" name="action" value="actualizar">
                            <input type="hidden" name="id" value="<%= u.getId() %>">
                            <div class="modal-header">
                                <h5 class="modal-title">Editar Usuario</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                            </div>
                            <div class="modal-body">
                                <div class="mb-3">
                                    <label>Nombre</label>
                                    <input type="text" name="nombre" class="form-control" value="<%= u.getUsername() %>" required>
                                </div>
                                <div class="mb-3">
                                    <label>Correo</label>
                                    <input type="email" name="correo" class="form-control" value="<%= u.getCorreo() %>" required>
                                </div>
                                <div class="mb-3">
                                    <label>Rol</label>
                                    <select name="rol" class="form-select">
                                        <option value="estudiante" <%= "estudiante".equals(u.getRol()) ? "selected" : "" %>>Estudiante</option>
                                        <option value="docente" <%= "docente".equals(u.getRol()) ? "selected" : "" %>>Docente</option>
                                        <option value="administrador" <%= "administrador".equals(u.getRol()) ? "selected" : "" %>>Administrador</option>
                                    </select>
                                </div>
                                <p class="text-muted"><em>La contraseña no puede ser editada por el administrador.</em></p>
                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary">Guardar Cambios</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                            </div>
                        </form>
                    </div>
                </div>
            <% } %>
            </tbody>
        </table>
    </div>
</div>

<!-- Modales adicionales (crear/eliminar) conservan Bootstrap) -->
<!-- Modal Crear -->
<div class="modal fade" id="modalNuevo" tabindex="-1">
    <div class="modal-dialog">
        <form action="GestionUsuariosController" method="post" class="modal-content">
            <input type="hidden" name="action" value="crear">
            <div class="modal-header">
                <h5 class="modal-title">Crear Usuario</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div class="mb-3">
                    <label>Nombre</label>
                    <input type="text" name="nombre" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label>Correo</label>
                    <input type="email" name="correo" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label>Contraseña Inicial</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label>Rol</label>
                    <select name="rol" class="form-select" required>
                        <option value="estudiante">Estudiante</option>
                        <option value="docente">Docente</option>
                        <option value="administrador">Administrador</option>
                    </select>
                </div>
            </div>
            <div class="modal-footer">
                <button type="submit" class="btn btn-success">Crear Usuario</button>
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
            </div>
        </form>
    </div>
</div>

<!-- Modal de confirmación para eliminar -->
<div class="modal fade" id="modalEliminarUsuario" tabindex="-1" aria-labelledby="modalEliminarUsuarioLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <form action="GestionUsuariosController" method="post">
        <div class="modal-header bg-danger text-white">
          <h5 class="modal-title" id="modalEliminarUsuarioLabel">Confirmar Eliminación</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
        </div>
        <div class="modal-body">
          ¿Estás seguro de que deseas eliminar este usuario?
        </div>
        <input type="hidden" name="action" value="eliminar">
        <input type="hidden" name="id" id="idUsuarioEliminar">
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
          <button type="submit" class="btn btn-danger">Eliminar</button>
        </div>
      </form>
    </div>
  </div>
</div>

<c:if test="${mostrarModalNuevo}">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            $('#modalNuevoUsuario').modal('show');
        });
    </script>
</c:if>

<c:if test="${mostrarModalEditar}">
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            $('#modalEditarUsuario').modal('show');
        });
    </script>
</c:if>
