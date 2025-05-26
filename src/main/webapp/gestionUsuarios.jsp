<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario" %>
<%@page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !"administrador".equals(usuario.getRol())) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Los atributos ya vienen del request
%>
<html>
<head>
    <title>Gestión de Usuarios</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">Gestión de Usuarios</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <c:if test="${not empty exito}">
            <div class="alert alert-success">${exito}</div>
        </c:if>

        <!-- Filtros de búsqueda -->
        <div class="card mb-4">
            <div class="card-header">
                <h5 class="mb-0">Filtrar Usuarios</h5>
            </div>
            <div class="card-body">
                <form method="get" action="GestionUsuariosController" class="form-inline row g-3">
                    <div class="col-md-4">
                        <label for="filtro" class="form-label">Filtrar por:</label>
                        <select name="filtro" class="form-control form-select">
                            <option value="">Todos</option>
                            <option value="id" ${filtro == 'id' ? 'selected' : ''}>ID</option>
                            <option value="nombre" ${filtro == 'nombre' ? 'selected' : ''}>Nombre</option>
                            <option value="correo" ${filtro == 'correo' ? 'selected' : ''}>Correo</option>
                            <option value="rol" ${filtro == 'rol' ? 'selected' : ''}>Rol</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label for="valor" class="form-label">Valor:</label>
                        <input type="text" name="valor" class="form-control" placeholder="Buscar..." value="${valor != null ? valor : ''}" />
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
                            <c:choose>
                                <c:when test="${not empty usuarios}">
                                    <c:forEach var="u" items="${usuarios}">
                                        <tr>
                                            <td>${u.id}</td>
                                            <td>${u.nombre}</td>
                                            <td>${u.correo}</td>
                                            <td>
                                                <span class="badge
                                                    ${u.rol == 'administrador' ? 'bg-primary' :
                                                       u.rol == 'docente' ? 'bg-success' : 'bg-info'}">
                                                    ${u.rol}
                                                </span>
                                            </td>
                                            <td>
                                                <a href="GestionUsuariosController?action=editar&id=${u.id}" class="btn btn-sm btn-warning" title="Editar">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <form method="post" action="GestionUsuariosController" style="display:inline;">
                                                    <input type="hidden" name="action" value="eliminar" />
                                                    <input type="hidden" name="id" value="${u.id}" />
                                                    <button type="submit" class="btn btn-sm btn-danger"
                                                            onclick="return confirm('¿Está seguro de eliminar este usuario?')"
                                                            title="Eliminar">
                                                        <i class="fas fa-trash-alt"></i>
                                                    </button>
                                                </form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="5" class="text-center">No se encontraron usuarios</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
