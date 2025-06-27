// Elementos del DOM
const sidebarToggle = document.getElementById('sidebarToggle');
const sidebar = document.getElementById('sidebar');
const mainContent = document.getElementById('mainContent');
const overlay = document.getElementById('overlay');
const sidebarTexts = document.querySelectorAll('.sidebar-text'); // Asegúrate de que esto siga siendo relevante

// Toggle sidebar - Comportamiento unificado
sidebarToggle.addEventListener('click', () => {
    if (window.innerWidth <= 768) {
        const isOpening = !sidebar.classList.contains('active');
        sidebar.classList.toggle('active');
        overlay.classList.toggle('active');
        document.body.style.overflow = isOpening ? 'hidden' : '';
        if (isOpening) {
            document.querySelector('nav').style.zIndex = '45';
        } else {
            document.querySelector('nav').style.zIndex = '30';
        }
    } else {
        sidebar.classList.toggle('sidebar-collapsed');
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
        sidebar.classList.remove('active');
        sidebar.style.left = '';
        overlay.classList.remove('active');
        document.body.style.overflow = '';
    } else {
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
        sidebar.classList.add('sidebar-expanded');
        mainContent.classList.add('content-expanded');
    }
    loadInitialPage();
    // initEventListeners() se llama dentro de loadPage para asegurar que los listeners
    // se adjunten a los elementos recién cargados.
});

// Dropdown perfil
const profileDropdown = document.getElementById('profileDropdown');
const dropdownMenu = document.getElementById('dropdownMenu');

if (profileDropdown && dropdownMenu) {
    profileDropdown.addEventListener('click', (e) => {
        e.stopPropagation();
        dropdownMenu.classList.toggle('hidden');
    });

    document.addEventListener('click', (event) => {
        if (!profileDropdown.contains(event.target)) {
            dropdownMenu.classList.add('hidden');
        }
    });
}

// Sistema de enrutamiento mejorado
const routes = {
    'resumenAdmin.jsp': 'resumenAdmin.jsp',
    'GestionUsuariosController': 'gestion_usuarios.jsp',
    'adminAsignaturas.jsp': 'adminAsignaturas.jsp',
    'adminCursos.jsp': 'adminCursos.jsp',
    'reportes.jsp': 'reportes.jsp',
    'configuracion.jsp': 'configuracion.jsp',
};

// Función para cargar y ejecutar scripts dinámicamente
// Agregué la variable appCtx para que la ruta sea dinámica y robusta
async function loadScript(scriptPath) {
    return new Promise((resolve, reject) => {
        const script = document.createElement('script');
        script.src = scriptPath;
        script.onload = resolve;
        script.onerror = reject;
        document.body.appendChild(script);
    });
}

// Cargar página con manejo de estado
async function loadPage(page) {
    try {
        const pageToLoad = routes[page] || page;

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

        // Lógica para cargar y ejecutar el script específico de la página
        if (page === 'configuracion.jsp') {
            // Usa la variable global appCtx (que debes definir en adminDashboard.jsp)
            const scriptPath = appCtx + '/js/configuracion.js';
            
            // Solo carga el script si aún no está en el DOM
            if (!document.querySelector(`script[src="${scriptPath}"]`)) {
                await loadScript(scriptPath);
            }
            
            // Llama a la función de inicialización del script de configuración
            // Un pequeño retraso para asegurar que el DOM se asiente
            setTimeout(() => {
                 if (typeof initializeConfigPage === 'function') {
                     initializeConfigPage();
                 } else {
                     console.error("Error: initializeConfigPage no está definido. Asegúrate que js/configuracion.js se carga y define esta función.");
                 }
            }, 50);
        }

        // Manejar el historial
        if (pageToLoad !== window.location.pathname) {
            window.history.pushState({page}, '', `?page=${page}`);
        }

        initEventListeners(); // Re-adjuntar listeners para elementos comunes
    } catch (error) {
        console.error('Error cargando página:', error);
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

// Inicializar event listeners (para elementos que están siempre en el DOM)
function initEventListeners() {
    document.querySelectorAll('.sidebar-link').forEach(link => {
        link.removeEventListener('click', handleSidebarLinkClick);
        link.addEventListener('click', handleSidebarLinkClick);
    });

    document.querySelectorAll('form[data-ajax]').forEach(form => {
        form.removeEventListener('submit', handleAjaxFormSubmit);
        form.addEventListener('submit', handleAjaxFormSubmit);
    });
}

// Funciones manejadoras de eventos separadas para poder removerlas y evitar duplicados
function handleSidebarLinkClick(e) {
    e.preventDefault();
    const page = this.getAttribute('data-page');
    if (page) {
        loadPage(page);
    }
}

async function handleAjaxFormSubmit(e) {
    e.preventDefault();
    const form = this;
    const formData = new FormData(form);

    try {
        const response = await fetch(form.action, {
            method: form.method,
            body: formData
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(`Error ${response.status}: ${response.statusText}. Detalle: ${errorText}`);
        }

        const result = await response.text();
        mainContent.innerHTML = result;
        initEventListeners();

        // Si el formulario que se envía es el de 'configForm', re-inicializa su script
        if (form.id === 'configForm') {
            setTimeout(() => {
                if (typeof initializeConfigPage === 'function') {
                    initializeConfigPage();
                } else {
                    console.error("Error: initializeConfigPage no está definido después de enviar configForm.");
                }
            }, 50);
        }
        
    } catch (error) {
        console.error('Error al enviar formulario AJAX:', error);
        mainContent.innerHTML = `
                    <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative" role="alert">
                        <strong>Error al procesar formulario:</strong> ${error.message}
                    </div>
                `;
    }
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
