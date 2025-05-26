
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario" %>
<!DOCTYPE html>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !"administrador".equals(usuario.getRol())) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    Usuario editar = (Usuario) request.getAttribute("usuario");
    if (editar == null) {
        response.sendRedirect("GestionUsuariosController");
        return;
    }
    
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <title>Editar Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Editar Usuario</h5>
                    </div>
                    <div class="card-body">
                        <% if (error != null) { %>
                        <div class="alert alert-danger"><%= error %></div>
                        <% } %>
                        
                        <form method="post" action="GestionUsuariosController">
                            <input type="hidden" name="action" value="actualizar">
                            <input type="hidden" name="id" value="<%= editar.getId() %>">
                            
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="nombre" 
                                       value="<%= editar.getNombre() %>" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="correo" class="form-label">Correo Electrónico</label>
                                <input type="email" class="form-control" id="correo" name="correo" 
                                       value="<%= editar.getCorreo() %>" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="rol" class="form-label">Rol</label>
                                <select class="form-select" id="rol" name="rol" required>
                                    <option value="administrador" <%= "administrador".equals(editar.getRol()) ? "selected" : "" %>>Administrador</option>
                                    <option value="docente" <%= "docente".equals(editar.getRol()) ? "selected" : "" %>>Docente</option>
                                    <option value="estudiante" <%= "estudiante".equals(editar.getRol()) ? "selected" : "" %>>Estudiante</option>
                                </select>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="GestionUsuariosController" class="btn btn-secondary me-md-2">
                                    <i class="fas fa-times"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Guardar Cambios
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>