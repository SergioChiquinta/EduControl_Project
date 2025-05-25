
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario" %>
<!DOCTYPE html>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    if (usuario == null || !"administrador".equals(usuario.getRol())) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    Usuario nuevo = (Usuario) request.getAttribute("usuario");
    if (nuevo == null) {
        nuevo = new Usuario();
    }
    
    String error = (String) request.getAttribute("error");
%>
<html>
<head>
    <title>Nuevo Usuario</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-header">
                        <h5 class="mb-0">Crear Nuevo Usuario</h5>
                    </div>
                    <div class="card-body">
                        <% if (error != null) { %>
                        <div class="alert alert-danger"><%= error %></div>
                        <% } %>
                        
                        <form method="post" action="GestionUsuariosController">
                            <input type="hidden" name="action" value="crear">
                            
                            <div class="mb-3">
                                <label for="nombre" class="form-label">Nombre</label>
                                <input type="text" class="form-control" id="nombre" name="nombre" 
                                       value="<%= nuevo.getNombre() != null ? nuevo.getNombre() : "" %>" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="correo" class="form-label">Correo Electrónico</label>
                                <input type="email" class="form-control" id="correo" name="correo" 
                                       value="<%= nuevo.getCorreo() != null ? nuevo.getCorreo() : "" %>" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="rol" class="form-label">Rol</label>
                                <select class="form-select" id="rol" name="rol" required>
                                    <option value="">Seleccione un rol</option>
                                    <option value="administrador" <%= "administrador".equals(nuevo.getRol()) ? "selected" : "" %>>Administrador</option>
                                    <option value="docente" <%= "docente".equals(nuevo.getRol()) ? "selected" : "" %>>Docente</option>
                                    <option value="estudiante" <%= "estudiante".equals(nuevo.getRol()) ? "selected" : "" %>>Estudiante</option>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label for="password" class="form-label">Contraseña</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="confirm_password" class="form-label">Confirmar Contraseña</label>
                                <input type="password" class="form-control" id="confirm_password" name="confirm_password" required>
                            </div>
                            
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <a href="GestionUsuariosController" class="btn btn-secondary me-md-2">
                                    <i class="fas fa-times"></i> Cancelar
                                </a>
                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-save"></i> Guardar Usuario
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Validar que las contraseñas coincidan
        document.querySelector('form').addEventListener('submit', function(e) {
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirm_password').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('Las contraseñas no coinciden');
            }
        });
    </script>
</body>
</html>