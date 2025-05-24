
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.Usuario" %>
<!DOCTYPE html>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!-- Resto de tu código HTML/JSP -->
<html>
<head>
    <title>Dashboard - Menú</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
    <jsp:include page="menu.jsp" />
</body>

<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>

</html>