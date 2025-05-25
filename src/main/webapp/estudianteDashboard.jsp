
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !u.getRol().equalsIgnoreCase("estudiante")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String mensaje = (String) session.getAttribute("mensaje_bienvenida");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel de Estudiante</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-success">
        <div class="container">
            <a class="navbar-brand" href="#">EduControl</a>
            <span class="navbar-text text-white ms-auto">
                <%= u.getNomUsu() %> (Estudiante)
            </span>
            <a href="LogoutController" class="btn btn-outline-light ms-3">Cerrar sesión</a>
        </div>
    </nav>
    
    <div class="container mt-5">
        <div class="alert alert-warning">
            <h3><%= mensaje %></h3>
        </div>
        
        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-card-checklist"></i> Mis Notas</h5>
                        <p class="card-text">Consulta tus calificaciones por curso.</p>
                        <a href="misNotas.jsp" class="btn btn-primary">Consultar</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-graph-up"></i> Mi Progreso</h5>
                        <p class="card-text">Visualiza tu progreso académico.</p>
                        <a href="miProgreso.jsp" class="btn btn-primary">Ver</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>