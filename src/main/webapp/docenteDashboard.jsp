
<%@page import="Model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !u.getRol().equalsIgnoreCase("docente")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String mensaje = (String) session.getAttribute("mensaje_bienvenida");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Panel de Docente</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="#">EduControl</a>
            <span class="navbar-text text-white ms-auto">
                <%= u.getUsername()%> (Docente)
            </span>
            <a href="LogoutController" class="btn btn-outline-light ms-3">Cerrar sesión</a>
        </div>
    </nav>
    
    <div class="container mt-5">
        <div class="alert alert-info">
            <h3><%= mensaje %></h3>
        </div>
        
        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-journal-text"></i> Registro de Notas</h5>
                        <p class="card-text">Registra y actualiza las notas de tus estudiantes.</p>
                        <a href="registroNotas.jsp" class="btn btn-primary">Acceder</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title"><i class="bi bi-people"></i> Mis Estudiantes</h5>
                        <p class="card-text">Consulta el listado de tus estudiantes.</p>
                        <a href="misEstudiantes.jsp" class="btn btn-primary">Acceder</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="assets/js/bootstrap.bundle.min.js"></script>
</body>
</html>