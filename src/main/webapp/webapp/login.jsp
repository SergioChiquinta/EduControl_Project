
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - EduControl</title>
    <link rel="stylesheet" href="assets/css/bootstrap.min.css">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <h3 class="text-center">Iniciar Sesión</h3>
            <form action="LoginController" method="post">
                <div class="mb-3">
                    <label for="usuario" class="form-label">Usuario</label>
                    <input type="text" class="form-control" id="usuario" name="usuario" required>
                </div>
                <div class="mb-3">
                    <label for="clave" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="clave" name="clave" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Entrar</button>
                <%
                    if (request.getAttribute("error") != null) {
                        out.println("<div class='mt-3 text-danger text-center'>" + request.getAttribute("error") + "</div>");
                    }
                %>
            </form>
        </div>
    </div>
</div>
</body>
</html>

