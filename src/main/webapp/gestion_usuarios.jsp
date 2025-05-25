
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

<% if (error != null) { %>
<div class="alert alert-danger"><%= error %></div>
<% } %>

<% if (exito != null) { %>
<div class="alert alert-success"><%= exito %></div>
<% } %>

<!DOCTYPE html>
<html>
<head>
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">Gestión de Usuarios</h1>
        
        <% if (error != null) { %>
        <div class="alert alert-danger"><%= error %></div>
        <% } %>
        
        <% if (exito != null) { %>
        <div class="alert alert-success"><%= exito %></div>
        <% } %>
        
        <!-- Filtros de búsqueda -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="mb-0">Filtrar Usuarios</h5>
            </div>
            <div class="card-body">
                <form method="get" action="GestionUsuariosController" class="row g-3">
                    <div class="col-md-4">
                        <label for="filtro" class="form-label">Filtrar por:</label>
                        <select id="filtro" name="filtro" class="form-select">
                            <option value="">Todos</option>
                            <option value="id" <%= "id".equals(filtro) ? "selected" : "" %>>ID</option>
                            <option value="nombre" <%= "nombre".equals(filtro) ? "selected" : "" %>>Nombre</option>
                            <option value="correo" <%= "correo".equals(filtro) ? "selected" : "" %>>Correo</option>
                            <option value="rol" <%= "rol".equals(filtro) ? "selected" : "" %>>Rol</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label for="valor" class="form-label">Valor:</label>
                        <input type="text" id="valor" name="valor" class="form-control" 
                               value="<%= valor != null ? valor : "" %>">
                    </div>
                    <div class="col-md-2 d-flex align-items-end">
                        <button type="submit" class="btn btn-primary me-2">
                            <i class="fas fa-search"></i> Buscar
                        </button>
                        <a href="GestionUsuariosController" class="btn btn-secondary">
                            <i class="fas fa-sync-alt"></i>
                        </a>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Tabla de usuarios -->
        <div class="card">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="mb-0">Lista de Usuarios</h5>
                <a href="GestionUsuariosController?action=nuevo" class="btn btn-success">
                    <i class="fas fa-plus"></i> Nuevo Usuario
                </a>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nombre</th>
                                <th>Correo</th>
                                <th>Rol</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% if (usuarios != null && !usuarios.isEmpty()) { %>
                                <% for (Usuario u : usuarios) { %>
                                <tr>
                                    <td><%= u.getId() %></td>
                                    <td><%= u.getUsername()%></td>
                                    <td><%= u.getCorreo() %></td>
                                    <td>
                                        <span class="badge 
                                            <%= "administrador".equals(u.getRol()) ? "bg-primary" : 
                                               "docente".equals(u.getRol()) ? "bg-success" : "bg-info" %>">
                                            <%= u.getRol() %>
                                        </span>
                                    </td>
                                    <td>
                                        <a href="GestionUsuariosController?action=editar&id=<%= u.getId() %>" 
                                           class="btn btn-sm btn-warning" title="Editar">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <form method="post" action="GestionUsuariosController" style="display:inline;">
                                            <input type="hidden" name="action" value="eliminar">
                                            <input type="hidden" name="id" value="<%= u.getId() %>">
                                            <button type="submit" class="btn btn-sm btn-danger" 
                                                    onclick="return confirm('¿Está seguro de eliminar este usuario?')"
                                                    title="Eliminar">
                                                <i class="fas fa-trash-alt"></i>
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                                <% } %>
                            <% } else { %>
                                <tr>
                                    <td colspan="5" class="text-center">No se encontraron usuarios</td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>