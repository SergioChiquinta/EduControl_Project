
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="Model.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard.jsp">EduControl</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="dashboard.jsp">Inicio</a>
                </li>
                
                <% if ("admin".equals(u.getRolUsu())) { %>
                <li class="nav-item">
                    <a class="nav-link" href="gestion_usuarios.jsp">Gestión de Usuarios</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="reportes.jsp">Reportes</a>
                </li>
                <% } %>
                
                <% if ("docente".equals(u.getRolUsu())) { %>
                <li class="nav-item">
                    <a class="nav-link" href="mis_cursos.jsp">Mis Cursos</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="calificaciones.jsp">Calificaciones</a>
                </li>
                <% } %>
                
                <% if ("estudiante".equals(u.getRolUsu())) { %>
                <li class="nav-item">
                    <a class="nav-link" href="mis_asignaturas.jsp">Mis Asignaturas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="mis_notas.jsp">Mis Notas</a>
                </li>
                <% } %>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                        <%= u.getNomUsu() %> (<%= u.getRolUsu() %>)
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li><a class="dropdown-item" href="perfil.jsp">Mi Perfil</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li>
                            <form action="LogoutController" method="post">
                                <button type="submit" class="dropdown-item text-danger">Cerrar Sesión</button>
                            </form>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>
                    
<h2>Bienvenido <%= u.getRolUsu() %>: <%= u.getNomUsu() %></h2>
