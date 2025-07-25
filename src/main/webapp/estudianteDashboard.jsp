
<%@page import="Model.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Model.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !u.getRol().equalsIgnoreCase("estudiante")) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Dashboard Docente - Newton College</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="css/estilos_generales.css">
    </head>
    <body class="bg-gray-100 flex flex-col min-h-screen">
        <!-- Navbar -->
        <nav class="bg-[#03397b] text-white p-4 fixed top-0 w-full z-30 flex items-center justify-between">
            <div class="flex items-center">
                <button id="sidebarToggle" class="mr-4 text-white focus:outline-none block">
                    <i class="fas fa-bars text-xl"></i>
                </button>
            </div>
            <div class="absolute left-1/2 transform -translate-x-1/2 flex items-center space-x-2">
                <img alt="Newton College official seal logo in white on blue background" class="h-8 w-auto" height="150" src="https://storage.googleapis.com/a1aa/image/635bb1ce-b2ad-4490-4f10-e0205cbcadc9.jpg" width="150" />
                <a href="docenteDashboard.jsp" class="font-bold text-lg hover:underline">Newton College</a>
            </div>
            <div class="relative">
                <button id="profileDropdown" class="flex items-center text-white focus:outline-none">
                    <span class="mr-2"><%= u.getUsername() %></span>
                    <i class="fas fa-chevron-down"></i>
                </button>
                <div id="dropdownMenu" class="hidden absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-20">
                    <a href="LogoutController" class="block px-4 py-2 text-gray-800 hover:bg-blue-100">
                        <i class="fas fa-sign-out-alt mr-2"></i>Cerrar Sesión
                    </a>
                </div>
            </div>
        </nav>

        <!-- Sidebar -->
        <div id="sidebar" class="sidebar sidebar-expanded text-white fixed h-[calc(100vh-64px)] z-10 bg-[#03397b]" style="top:64px; overflow-y:auto;">
            <div class="p-4">
                <ul>
                    <li class="mb-2">
                        <a href="#" data-page="ResumenEstudianteController" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                            <i class="fas fa-tachometer-alt text-center w-6"></i>
                            <span class="sidebar-text ml-3">Dashboard</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" data-page="EstudianteNotasController" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                            <i class="fas fa-users text-center w-6"></i>
                            <span class="sidebar-text ml-3">Mis Notas</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" data-page="reportes.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                            <i class="fas fa-book text-center w-6"></i>
                            <span class="sidebar-text ml-3">Reportes</span>
                        </a>
                    </li>
                    <li class="mb-2">
                        <a href="#" data-page="configuracion.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                            <i class="fas fa-cog text-center w-6"></i>
                            <span class="sidebar-text ml-3">Configuración</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

        <div id="overlay" class="hidden fixed inset-0 bg-black bg-opacity-50 z-20"></div>

        <!-- Main Content -->
        <div id="mainContent" class="main-content content-expanded pt-20 p-6" style="transition: margin-left 0.3s ease">
            <!-- Aquí va tu contenido -->
        </div>

        <script src="js/estudianteDashboard.js"></script>
        <script src="js/estudianteNotas.js"></script>
        <!-- Bootstrap JS Bundle (incluye Popper) -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

    </body>
</html>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        const defaultLink = document.querySelector('a.sidebar-link[data-page="ResumenEstudianteController"]');
        if (defaultLink) {
            defaultLink.click();
        }
    });
</script>

<script>
    window.addEventListener('mouseover', initLandbot, {once: true});
    window.addEventListener('touchstart', initLandbot, {once: true});
    var myLandbot;
    function initLandbot() {
        if (!myLandbot) {
            var s = document.createElement('script');
            s.type = "module"
            s.async = true;
            s.addEventListener('load', function () {
                var myLandbot = new Landbot.Livechat({
                    configUrl: 'https://storage.googleapis.com/landbot.online/v3/H-3037486-N0Y11QE0VKQW4VM7/index.json',
                });
            });
            s.src = 'https://cdn.landbot.io/landbot-3/landbot-3.0.0.mjs';
            var x = document.getElementsByTagName('script')[0];
            x.parentNode.insertBefore(s, x);
        }
    }
</script>
