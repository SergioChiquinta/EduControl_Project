
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - EduControl</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body class="bg-light">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <h3 class="text-center mb-4">Iniciar Sesión</h3>
            <form action="LoginController" method="post">
                <div class="mb-3">
                    <label for="usuario">Usuario</label>
                    <input type="text" name="usuario" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label for="clave">Contraseña</label>
                    <input type="password" name="clave" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Ingresar</button>
                <% if (request.getAttribute("error") != null) { %>
                    <div class="mt-3 alert alert-danger text-center">
                        <%= request.getAttribute("error") %>
                    </div>
                <% } %>
            </form>
        </div>
    </div>
</div>
</body>
</html>


