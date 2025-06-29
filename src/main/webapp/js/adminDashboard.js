
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
    'resumenAdmin.jsp': 'resumenAdmin.jsp',
    'GestionUsuariosController': 'GestionUsuariosController',
    'AsignaturasAdminController': 'AsignaturasAdminController',
    'CursosAdminController': 'CursosAdminController',
    'reportes.jsp': 'ReporteController',
    'configuracion.jsp': 'ConfiguracionController'
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

        if (!response.ok)
            throw new Error(`Error ${response.status}: ${response.statusText}`);

        const html = await response.text();
        mainContent.innerHTML = html;

        if (page === 'reportes.jsp') {
            loadReportesScript(() => {
                initReportesPage(); // ✅ se llama luego de cargar el script
            });
        }
        
        else if (page === 'configuracion.jsp') {
            loadConfiguracionScript(() => {
                initConfiguracionPage();
            });
        }
        
        else if (page === 'GestionUsuariosController') {
            loadGestionUsuariosScript(() => {
                initGestionUsuariosPage();
            });
        }
        
        else if (page === 'AsignaturasAdminController') {
            loadAsignaturasScript(() => {
                initAsignaturasPage();
            });
        }
        
        else if (page === 'CursosAdminController') {
            loadCursosScript(() => {
                initCursosPage();
            });
        }
        
        // Manejar el historial
        if (pageToLoad !== window.location.pathname) {
            window.history.pushState({page}, '', `?page=${page}`);
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
        loadPage('resumenAdmin.jsp');
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

                if (!response.ok)
                    throw new Error('Error en la solicitud');

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
        loadPage('resumenAdmin.jsp');
    }
}

function loadReportesScript(callback) {
    const oldScript = document.querySelector("script[data-reportes]");
    if (oldScript)
        oldScript.remove();

    const script = document.createElement("script");
    script.src = "js/reportes.js?t=" + new Date().getTime();
    script.type = "text/javascript";
    script.setAttribute("data-reportes", "true");

    script.onload = () => {
        console.log("✅ reportes.js cargado correctamente");
        if (typeof callback === "function")
            callback(); // ✅ ejecuta callback tras carga
    };

    script.onerror = () => console.error("❌ Error al cargar reportes.js");

    document.body.appendChild(script);
}

function loadConfiguracionScript(callback) {
    if (window.configuracionScriptLoaded) {
        callback();
        return;
    }

    const script = document.createElement("script");
    script.src = "js/configuracion.js?t=" + new Date().getTime();
    script.defer = true;
    script.onload = () => {
        window.configuracionScriptLoaded = true;
        callback();
    };
    document.body.appendChild(script);
}

function loadGestionUsuariosScript(callback) {
    if (window.gestionUsuariosScriptLoaded) {
        callback();
        return;
    }

    const script = document.createElement("script");
    script.src = "js/gestionUsuariosAdmin.js?t=" + new Date().getTime();
    script.defer = true;
    script.onload = () => {
        window.gestionUsuariosScriptLoaded = true;
        callback();
    };
    document.body.appendChild(script);
}

function loadAsignaturasScript(callback) {
    if (window.asignaturasScriptLoaded) {
        callback();
        return;
    }

    const script = document.createElement("script");
    script.src = "js/adminAsignaturas.js?t=" + new Date().getTime();
    script.defer = true;
    script.onload = () => {
        window.asignaturasScriptLoaded = true;
        callback();
    };
    document.body.appendChild(script);
}

function loadCursosScript(callback) {
    if (window.cursosScriptLoaded) {
        callback();
        return;
    }

    const script = document.createElement("script");
    script.src = "js/adminCursos.js?t=" + new Date().getTime();
    script.defer = true;
    script.onload = () => {
        window.cursosScriptLoaded = true;
        callback();
    };
    document.body.appendChild(script);
}
