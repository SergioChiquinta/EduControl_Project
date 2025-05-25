
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="Model.Usuario" %>
<%
    Usuario u = (Usuario) session.getAttribute("usuario");
    if (u == null || !u.getRol().equalsIgnoreCase("administrador")) {
        response.sendRedirect("login.jsp");
        return;
    }

    String mensaje = (String) session.getAttribute("mensaje_bienvenida");
    
    Usuario usuario = (Usuario) session.getAttribute("usuario");
    System.out.println("Usuario en sesión: " + usuario); // Agregar log
    if (usuario == null) {
        System.out.println("No hay usuario en sesión"); // Log
        response.sendRedirect("login.jsp");
        return;
    }
    if (!"administrador".equals(usuario.getRol())) {
        System.out.println("Rol incorrecto: " + usuario.getRol()); // Log
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Dashboard Admin - Newton College</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet" />
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        .sidebar {
            transition: all 0.3s;
        }
        .sidebar-collapsed {
            width: 90px;
        }
        .sidebar-expanded {
            width: 250px;
        }
        .main-content {
            transition: all 0.3s;
        }
        .content-expanded {
            margin-left: 250px;
        }
        .content-collapsed {
            margin-left: 90px;
        }
        @media (max-width: 768px) {
            .sidebar-expanded, .sidebar-collapsed {
                width: 100% !important;
                height: auto;
                position: relative;
            }
            .main-content {
                margin-left: 0 !important;
            }
        }
    </style>
</head>
<body class="bg-gray-100">
    <!-- Navbar -->
    <nav class="bg-[#03397b] text-white p-4 fixed w-full z-10 flex items-center justify-between">
        <!-- Izquierda: botón sidebar -->
        <div class="flex items-center">
            <button id="sidebarToggle" class="mr-4 text-white focus:outline-none">
                <i class="fas fa-bars text-xl"></i>
            </button>
        </div>

        <!-- Centro: Logo + Texto centrados -->
        <div class="absolute left-1/2 transform -translate-x-1/2 flex items-center space-x-2">
            <img alt="Newton College official seal logo in white on blue background" class="h-8 w-auto" height="150"
                 src="https://storage.googleapis.com/a1aa/image/635bb1ce-b2ad-4490-4f10-e0205cbcadc9.jpg" width="150" />
            <a href="adminDashboard.jsp" class="font-bold text-lg hover:underline">Newton College</a>
        </div>

        <!-- Derecha: Perfil -->
        <div class="relative">
            <button id="profileDropdown" class="flex items-center text-white focus:outline-none">
                <span class="mr-2"><%= u.getUsername() %></span>
                <i class="fas fa-chevron-down"></i>
            </button>
            <div
                id="dropdownMenu"
                class="hidden absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg z-20"
            >
                <a href="#" class="block px-4 py-2 text-gray-800 hover:bg-blue-100">
                    <i class="fas fa-user-circle mr-2"></i>Mi Perfil
                </a>
                <a href="LogoutController" class="block px-4 py-2 text-gray-800 hover:bg-blue-100">
                    <i class="fas fa-sign-out-alt mr-2"></i>Cerrar Sesión
                </a>
            </div>
        </div>
    </nav>

    <!-- Sidebar -->
    <div
        id="sidebar"
        class="sidebar sidebar-expanded text-white fixed h-[calc(100vh-64px)] z-10 bg-[#03397b]"
        style="top:64px; overflow-y:auto;"
    >
        <div class="p-4">
            <ul>
                <li class="mb-2">
                <a href="#" data-page="resumenDashboard.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                    <i class="fas fa-tachometer-alt text-center w-6"></i>
                    <span class="sidebar-text ml-3">Dashboard</span>
                </a>
            </li>
            <li class="mb-2">
                <a href="#" data-page="GestionUsuariosController" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                    <i class="fas fa-users text-center w-6"></i>
                    <span class="sidebar-text ml-3">Gestión de Usuarios</span>
                </a>
            </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-chalkboard-teacher text-center w-6"></i>
                        <span class="sidebar-text ml-3">Docentes</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-user-graduate text-center w-6"></i>
                        <span class="sidebar-text ml-3">Estudiantes</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-book text-center w-6"></i>
                        <span class="sidebar-text ml-3">Asignaturas</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-door-open text-center w-6"></i>
                        <span class="sidebar-text ml-3">Cursos</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-tasks text-center w-6"></i>
                        <span class="sidebar-text ml-3">Asignaciones</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-file-import text-center w-6"></i>
                        <span class="sidebar-text ml-3">Importar Datos</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-file-export text-center w-6"></i>
                        <span class="sidebar-text ml-3">Reportes</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" class="flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-cog text-center w-6"></i>
                        <span class="sidebar-text ml-3">Configuración</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <!-- Main Content -->
    <div
        id="mainContent"
        class="main-content content-expanded pt-20 p-6"
        style="transition: margin-left 0.3s ease"
    >
        <!-- Aquí va tu contenido -->
    </div>

    <script>
        // Toggle sidebar
        const sidebarToggle = document.getElementById('sidebarToggle');
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.getElementById('mainContent');
        const sidebarTexts = document.querySelectorAll('.sidebar-text');

        sidebarToggle.addEventListener('click', () => {
            sidebar.classList.toggle('sidebar-collapsed');
            sidebar.classList.toggle('sidebar-expanded');
            mainContent.classList.toggle('content-collapsed');
            mainContent.classList.toggle('content-expanded');

            // Mostrar u ocultar texto
            sidebarTexts.forEach((text) => {
                text.classList.toggle('hidden');
            });
        });

        // Dropdown perfil
        const profileDropdown = document.getElementById('profileDropdown');
        const dropdownMenu = document.getElementById('dropdownMenu');

        profileDropdown.addEventListener('click', (e) => {
            e.stopPropagation();
            dropdownMenu.classList.toggle('hidden');
        });

        document.addEventListener('click', (event) => {
            if (!profileDropdown.contains(event.target)) {
                dropdownMenu.classList.add('hidden');
            }
        });
        
        async function loadPage(page) {
            try {
                // Cambiar para cargar a través del controlador
                let url = page;
                if(page === 'gestionUsuarios.jsp') {
                    url = 'GestionUsuariosController';
                }

                const response = await fetch(url);
                if (!response.ok) {
                    throw new Error('Error al cargar la página: ' + response.status);
                }
                const html = await response.text();
                mainContent.innerHTML = html;

                // Reiniciar event listeners para los nuevos elementos
                initEventListeners();
            } catch (error) {
                console.error('Error:', error);
                mainContent.innerHTML = `<div class="alert alert-danger">Error cargando la página: ${error.message}</div>`;
            }
        }

        function initEventListeners() {
            // Reasignar event listeners a los elementos dinámicos
            document.querySelectorAll('.sidebar-link').forEach(link => {
                link.addEventListener('click', (e) => {
                    e.preventDefault();
                    const page = link.getAttribute('data-page');
                    if (page) {
                        loadPage(page);
                    }
                });
            });
        }

        // Cargar página por defecto al iniciar
        loadPage('resumenDashboard.jsp');
        
    </script>
</body>
</html>
