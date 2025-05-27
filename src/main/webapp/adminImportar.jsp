
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="p-6 max-w-3xl mx-auto bg-white shadow rounded-lg">
  <h1 class="text-2xl font-bold mb-6 text-gray-800">Importar Datos Masivos</h1>

  <p class="mb-4 text-gray-700">
    Aquí puedes cargar archivos en formato <strong>Excel (.xlsx), CSV (.csv)</strong> o <strong>JSON (.json)</strong> para importar rápidamente
    <em>Salones</em>, <em>Docentes</em> o <em>Estudiantes</em>.  
  </p>

  <form action="ImportarDatosController" method="post" enctype="multipart/form-data" class="space-y-6">
    <div>
      <label for="tipoDato" class="block mb-1 font-semibold text-gray-700">Tipo de dato a importar</label>
      <select id="tipoDato" name="tipoDato" required
              class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500">
        <option value="" disabled selected>Selecciona una opción</option>
        <option value="salones">Salones</option>
        <option value="docentes">Docentes</option>
        <option value="estudiantes">Estudiantes</option>
      </select>
    </div>

    <div>
      <label for="archivo" class="block mb-1 font-semibold text-gray-700">Seleccionar archivo</label>
      <input type="file" id="archivo" name="archivo" accept=".xlsx,.csv,.json" required
             class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500" />
    </div>

    <div>
      <button type="submit" class="bg-blue-600 text-white px-6 py-2 rounded hover:bg-blue-700">
        Importar
      </button>
      <a href="plantillas/plantilla_importacion.xlsx" 
         class="ml-4 text-blue-600 hover:underline" download>
        Descargar plantilla ejemplo
      </a>
    </div>
  </form>

  <!-- Área para mostrar mensajes después de procesar -->
  <div id="mensajeResultado" class="mt-6 p-4 rounded bg-green-100 text-green-700 hidden"></div>
  <div id="mensajeError" class="mt-6 p-4 rounded bg-red-100 text-red-700 hidden"></div>
</div>

<script>
  // Ejemplo simple para mostrar mensaje luego de importar (sería mejor con AJAX)
  const form = document.querySelector('form');
  const mensajeResultado = document.getElementById('mensajeResultado');
  const mensajeError = document.getElementById('mensajeError');

  form.addEventListener('submit', () => {
    mensajeResultado.classList.add('hidden');
    mensajeError.classList.add('hidden');
  });

  // Aquí se podría añadir lógica para mostrar mensajes tras respuesta del servidor
</script>
