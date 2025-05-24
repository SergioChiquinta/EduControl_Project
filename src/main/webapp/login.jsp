
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>
            Login - EduControl
        </title>
        <script src="https://cdn.tailwindcss.com">
        </script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" />
        <style>
            @import url('https://fonts.googleapis.com/css2?family=Times+New+Roman&display=swap');
        </style>
    </head>

    <body class="bg-[#03397b] flex items-center justify-center min-h-screen font-[Times_New_Roman]">
        <div class="text-center">
            <img alt="Newton College official seal logo in white on blue background" class="mx-auto mb-3" height="120"
                 src="https://storage.googleapis.com/a1aa/image/635bb1ce-b2ad-4490-4f10-e0205cbcadc9.jpg" width="120" />
            <h1 class="text-white text-4xl font-bold mb-2">
                NEWTON
                <span class="font-normal">
                COLLEGE
                </span>
            </h1>
            <p class="text-white text-base mb-8">
                Founded 1979
            </p>
            <form action= "LoginController" method = "post" autocomplete="off" class="bg-[#d1d1d1] rounded-xl px-8 py-6 max-w-xs mx-auto">
                <h2 class="text-[#1a3a82] font-bold text-lg mb-5">
                    INICIAR SESIÓN
                </h2>
                <input class="w-full mb-3 rounded-full bg-[#bfbfbf] text-base text-center py-2 outline-none placeholder-gray-700 placeholder-opacity-100"
                       placeholder="Usuario" type="text" name="usuario" />

                <input class="w-full mb-5 rounded-full bg-[#bfbfbf] text-base text-center py-2 outline-none placeholder-gray-700 placeholder-opacity-100"
                       placeholder="Contraseña" type="password" name="clave" />
                <button class="bg-[#1a3a82] text-white text-base rounded-full px-6 py-2 mx-auto block" type="submit">
                    Ingresar
                </button>
                <% if (request.getAttribute("error") != null) {%>
                <div class="mt-3 alert alert-danger text-center">
                    <%= request.getAttribute("error")%>
                </div>
                <% }%>
            </form>
        </div>
    </body>
</html>


