
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !u.getRolUsu().equalsIgnoreCase("administrador")) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String mensaje = (String) session.getAttribute("mensaje_bienvenida");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel de Administrador</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">EduControl</a>
            <span class="navbar-text text-white ms-auto">
                <%= u.getNomUsu() %> (Administrador)
            </span>
            <a href="LogoutController" class="btn btn-outline-light ms-3">Cerrar sesión</a>
        </div>
    </nav>
    
    <div class="container mt-5">
        <div class="alert alert-success">
            <h3><%= mensaje %></h3>
        </div>
        
        <div class="row">
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-people-fill"></i> Gestión de Usuarios</h5>
                        <p class="card-text">Administra todos los usuarios del sistema.</p>
                        <a href="gestionUsuarios.jsp" class="btn btn-primary">Acceder</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-book"></i> Gestión de Cursos</h5>
                        <p class="card-text">Crea y administra cursos y asignaturas.</p>
                        <a href="gestionCursos.jsp" class="btn btn-primary">Acceder</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-graph-up"></i> Reportes Generales</h5>
                        <p class="card-text">Genera reportes del sistema.</p>
                        <a href="reportes.jsp" class="btn btn-primary">Acceder</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>