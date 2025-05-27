

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
    System.out.println("Usuario en sesión: " + usuario);
    if (usuario == null) {
        System.out.println("No hay usuario en sesión");
        response.sendRedirect("login.jsp");
        return;
    }
    if (!"administrador".equals(usuario.getRol())) {
        System.out.println("Rol incorrecto: " + usuario.getRol());
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
    /* Textos del sidebar siempre visibles en desktop */
    .sidebar-text {
        display: inline;
    }
    /* Ocultar textos cuando el sidebar está colapsado */
    .sidebar-collapsed .sidebar-text {
        display: none;
    }
    @media (max-width: 768px) {
        .sidebar {
            position: fixed;
            left: -250px; /* Oculto inicialmente */
            top: 64px;
            width: 250px;
            height: calc(100vh - 64px);
            transition: left 0.3s ease;
            z-index: 40;
        }
        .sidebar.active {
            left: 0; /* Mostrado */
        }
        .sidebar-collapsed, .sidebar-expanded {
            width: 250px; /* Mismo ancho en móvil */
        }
        .main-content {
            margin-left: 0 !important;
        }
        #overlay {
            display: none;
            position: fixed;
            top: 0; /* Cambiado de top: 64px a top: 0 para cubrir toda la pantalla */
            left: 0;
            right: 0;
            bottom: 0;
            background-color: rgba(0,0,0,0.5);
            z-index: 35; /* Mayor que el contenido (30) pero menor que sidebar (40) */
        }        
        #overlay.active {
            display: block;
        }        
        nav {
            z-index: 45 !important; /* Mayor que overlay pero menor que sidebar */
        }
    }
    .hidden {
        display: none !important;
    }
</style>
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
            <a href="adminDashboard.jsp" class="font-bold text-lg hover:underline">Newton College</a>
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
                    <a href="#" data-page="adminDocentes.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-chalkboard-teacher text-center w-6"></i>
                        <span class="sidebar-text ml-3">Docentes</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" data-page="adminEstudiantes.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-user-graduate text-center w-6"></i>
                        <span class="sidebar-text ml-3">Estudiantes</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" data-page="adminAsignaturas.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-book text-center w-6"></i>
                        <span class="sidebar-text ml-3">Asignaturas</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" data-page="adminCursos.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-door-open text-center w-6"></i>
                        <span class="sidebar-text ml-3">Cursos</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" data-page="adminImportar.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-file-import text-center w-6"></i>
                        <span class="sidebar-text ml-3">Importar Datos</span>
                    </a>
                </li>
                <li class="mb-2">
                    <a href="#" data-page="reportes.jsp" class="sidebar-link flex items-center py-2 px-4 rounded hover:bg-[#022f65]">
                        <i class="fas fa-file-export text-center w-6"></i>
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

    <script>
        // Elementos del DOM
        const sidebarToggle = document.getElementById('sidebarToggle');
        const sidebar = document.getElementById('sidebar');
        const mainContent = document.getElementById('mainContent');
        const overlay = document.getElementById('overlay');
        const sidebarTexts = document.querySelectorAll('.sidebar-text');

        // Toggle sidebar - Comportamiento unificado
        sidebarToggle.addEventListener('click', () => {
            if (window.innerWidth <= 768) {
                // Comportamiento para móviles/tablets
                const isOpening = !sidebar.classList.contains('active');
                sidebar.classList.toggle('active');
                overlay.classList.toggle('active');
                document.body.style.overflow = isOpening ? 'hidden' : '';

                // Asegurar que el navbar siga siendo clickeable
                if (isOpening) {
                    document.querySelector('nav').style.zIndex = '45';
                } else {
                    document.querySelector('nav').style.zIndex = '30';
                }
            } else {
                // Comportamiento para desktop
                const isCollapsed = sidebar.classList.toggle('sidebar-collapsed');
                sidebar.classList.toggle('sidebar-expanded');
                mainContent.classList.toggle('content-collapsed');
                mainContent.classList.toggle('content-expanded');
            }
        });

        // Cerrar sidebar al hacer clic en overlay (solo móviles)
        overlay.addEventListener('click', () => {
            if (window.innerWidth <= 768) {
                sidebar.classList.remove('active');
                overlay.classList.remove('active');
                document.body.style.overflow = '';
                document.querySelector('nav').style.zIndex = '30';
            }
        });

        // Manejar cambios de tamaño de pantalla
        window.addEventListener('resize', () => {
            if (window.innerWidth > 768) {
                // En desktop: asegurar visibilidad del sidebar y sin overlay
                sidebar.classList.remove('active');
                sidebar.style.left = '';
                overlay.classList.remove('active');
                document.body.style.overflow = '';
            } else {
                // En móvil: asegurar que el sidebar esté oculto inicialmente
                if (!sidebar.classList.contains('active')) {
                    overlay.classList.remove('active');
                }
            }
        });

        // Inicialización al cargar la página
        document.addEventListener('DOMContentLoaded', () => {
            if (window.innerWidth <= 768) {
                sidebar.classList.remove('active');
                overlay.classList.remove('active');
            } else {
                // Estado inicial para desktop
                sidebar.classList.add('sidebar-expanded');
                mainContent.classList.add('content-expanded');
            }

            // Resto de tu inicialización...
            loadInitialPage();
            initEventListeners();
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

        // Sistema de enrutamiento mejorado
        const routes = {
            'resumenDashboard.jsp': 'resumenDashboard.jsp',
            'GestionUsuariosController': 'gestion_usuarios.jsp',
            'adminDocentes.jsp': 'adminDocentes.jsp',
            'adminAsignaturas.jsp': 'adminAsignaturas.jsp',
            'adminCursos.jsp': 'adminCursos.jsp',
            'adminNotas.jsp': 'adminNotas.jsp',
            'reportes.jsp': 'reportes.jsp',
            'configuracion.jsp': 'configuracion.jsp',
        };

        // Cargar página con manejo de estado
        async function loadPage(page) {
            try {
                const pageToLoad = routes[page] || page;

                // Mostrar loader
                mainContent.innerHTML = `<div class="flex justify-center items-center h-64">
                    <div class="animate-spin rounded-full h-12 w-12 border-t-2 border-b-2 border-blue-500"></div>
                </div>`;

                const response = await fetch(pageToLoad, {
                    headers: {
                        'X-Requested-With': 'XMLHttpRequest'
                    }
                });

                if (!response.ok) throw new Error(`Error ${response.status}: ${response.statusText}`);

                const html = await response.text();
                mainContent.innerHTML = html;

                // Manejar el historial
                if (pageToLoad !== window.location.pathname) {
                    window.history.pushState({ page }, '', `?page=${page}`);
                }

                initEventListeners();
            } catch (error) {
                console.error('Error:', error);
                mainContent.innerHTML = `
                    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                        <strong>Error!</strong> ${error.message}
                    </div>
                `;
            }
        }

        // Manejar el evento de popstate (navegación adelante/atrás)
        window.addEventListener('popstate', (event) => {
            if (event.state && event.state.page) {
                loadPage(event.state.page);
            } else {
                loadPage('resumenDashboard.jsp');
            }
        });

        // Inicializar event listeners
        function initEventListeners() {
            // Sidebar links
            document.querySelectorAll('.sidebar-link').forEach(link => {
                link.addEventListener('click', (e) => {
                    e.preventDefault();
                    const page = link.getAttribute('data-page');
                    if (page) {
                        loadPage(page);
                    }
                });
            });

            // Forms que deben cargarse dentro del mainContent
            document.querySelectorAll('form[data-ajax]').forEach(form => {
                form.addEventListener('submit', async (e) => {
                    e.preventDefault();
                    const formData = new FormData(form);

                    try {
                        const response = await fetch(form.action, {
                            method: form.method,
                            body: formData
                        });

                        if (!response.ok) throw new Error('Error en la solicitud');

                        const result = await response.text();
                        mainContent.innerHTML = result;
                        initEventListeners();
                    } catch (error) {
                        console.error('Error:', error);
                    }
                });
            });
        }

        // Cargar página inicial basada en la URL o por defecto
        function loadInitialPage() {
            const urlParams = new URLSearchParams(window.location.search);
            const pageParam = urlParams.get('page');

            if (pageParam && routes[pageParam]) {
                loadPage(pageParam);
            } else {
                loadPage('resumenDashboard.jsp');
            }
        }
        
        function buscarUsuarios(form) {
            const formData = new FormData(form);
            const params = new URLSearchParams(formData);

            loadPage(`GestionUsuariosController?${params.toString()}`);
        }
        
        // Funciones para los modales
        function abrirModalNuevo() {
            document.getElementById('modalTitle').textContent = 'Nuevo Usuario';
            document.getElementById('modalAction').value = 'crear';
            document.getElementById('usuarioId').value = '';
            document.getElementById('nombre').value = '';
            document.getElementById('correo').value = '';
            document.getElementById('contraseña').value = '';
            document.getElementById('rol').value = 'administrador';
            document.getElementById('usuarioModal').classList.remove('hidden');
        }

        function abrirModalEditar(id, nombre, correo, rol) {
            document.getElementById('modalTitle').textContent = 'Editar Usuario';
            document.getElementById('modalAction').value = 'editar';
            document.getElementById('usuarioId').value = id;
            document.getElementById('nombre').value = nombre;
            document.getElementById('correo').value = correo;
            document.getElementById('contraseña').value = ""; // Dejar en blanco
            document.getElementById('contraseña').placeholder = "Dejar en blanco para no cambiar";
            document.getElementById('rol').value = rol;
            document.getElementById('usuarioModal').classList.remove('hidden');
        }

        function cerrarModal() {
            document.getElementById('usuarioModal').classList.add('hidden');
        }

        // Eliminación con confirmación
        let usuarioAEliminar = null;

        function confirmarEliminacion(id) {
            usuarioAEliminar = id;
            document.getElementById('confirmarModal').classList.remove('hidden');
        }

        function cerrarConfirmacion() {
            usuarioAEliminar = null;
            document.getElementById('confirmarModal').classList.add('hidden');
        }

        // Por esto:
        const confirmBtn = document.getElementById('confirmarEliminarBtn');
        if (confirmBtn) {
            confirmBtn.addEventListener('click', async function() {
                // tu código aquí
                if (!usuarioAEliminar) return;

                    const formData = new FormData();
                    formData.append('action', 'eliminar');
                    formData.append('id', usuarioAEliminar);

                    try {
                        const response = await fetch('GestionUsuariosController', {
                            method: 'POST',
                            body: formData,
                            headers: {
                                'X-Requested-With': 'XMLHttpRequest'
                            }
                        });

                        if (!response.ok) throw new Error('Error al eliminar');

                        const html = await response.text();
                        document.querySelector('#mainContent').innerHTML = html;
                        cerrarConfirmacion();
                    } catch (error) {
                        console.error('Error:', error);
                        alert('Error al eliminar el usuario');
                    }
            });
        }
        
        //Guardar cambios
        async function guardarUsuario(event) {
            event.preventDefault();
            const form = event.target;

            // Crear los datos como URLSearchParams en vez de FormData
            const formData = new URLSearchParams(new FormData(form));

            try {
                const response = await fetch('GestionUsuariosController', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: formData.toString() // Serializa para envío clásico
                });

                if (!response.ok) {
                    const errorText = await response.text();
                    throw new Error(errorText || 'Error al guardar');
                }

                const html = await response.text();
                document.querySelector('#mainContent').innerHTML = html;
                cerrarModal();
            } catch (error) {
                console.error('Error:', error);
                alert(error.message || 'Error al guardar el usuario');
            }
        }
    </script>
</body>
</html>
