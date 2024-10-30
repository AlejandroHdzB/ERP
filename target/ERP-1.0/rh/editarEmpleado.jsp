<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.cloudcomputing.erp.dto.EmpleadoDTO" %>

<%
    // Obtener el objeto EmpleadoDTO del request (cargado previamente en el servlet)
    EmpleadoDTO usuario = (EmpleadoDTO) request.getAttribute("usuario");
    if (usuario == null) {
        usuario = new EmpleadoDTO(); // Evitar errores si no hay datos
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Datos del Usuario</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #2C3E50;
        }
        label {
            display: block;
            margin-top: 10px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .btn {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #2C3E50;
            color: #ECF0F1;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 10px;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #34495E;
        }
    </style>
</head>
<body>

<div class="container">
    <h1>Editar Datos del Usuario</h1>
    <form action="editarUsuario" method="post">
        <label for="nombre">Nombre</label>
        <input type="text" name="nombre" id="nombre" value="<%= usuario.getNombre() %>" required>

        <label for="apellidoPaterno">Apellido Paterno</label>
        <input type="text" name="apellidoPaterno" id="apellidoPaterno" value="<%= usuario.getApellidoPaterno() %>" required>

        <label for="apellidoMaterno">Apellido Materno</label>
        <input type="text" name="apellidoMaterno" id="apellidoMaterno" value="<%= usuario.getApellidoMaterno() %>">

        <label for="email">Correo Electrónico</label>
        <input type="email" name="email" id="email" value="<%= usuario.getEmail() %>" required>

        <label for="pwd">Contraseña</label>
        <input type="password" name="pwd" id="pwd">

        <label for="telefono">Teléfono</label>
        <input type="text" name="telefono" id="telefono" value="<%= usuario.getTelefono() %>">

        <label for="calle">Calle</label>
        <input type="text" name="domicilio.calle" id="calle" value="<%= usuario.getDomicilio() != null ? usuario.getDomicilio().getCalle() : "" %>">

        <label for="noExt">Número Exterior</label>
        <input type="text" name="domicilio.noExt" id="noExt" value="<%= usuario.getDomicilio() != null ? usuario.getDomicilio().getNoExt() : "" %>">

        <label for="noInt">Número Interior</label>
        <input type="text" name="domicilio.noInt" id="noInt" value="<%= usuario.getDomicilio() != null ? usuario.getDomicilio().getNoInt() : "" %>">

        <label for="cp">Código Postal</label>
        <input type="text" name="domicilio.cp" id="cp" value="<%= usuario.getDomicilio() != null ? usuario.getDomicilio().getCp() : "" %>">

        <label for="ciudad">Ciudad</label>
        <input type="text" name="domicilio.ciudad" id="ciudad" value="<%= usuario.getDomicilio() != null ? usuario.getDomicilio().getCiudad() : "" %>">

        <label for="estado">Estado</label>
        <input type="text" name="domicilio.estado" id="estado" value="<%= usuario.getDomicilio() != null ? usuario.getDomicilio().getEstado() : "" %>">

        <button type="submit" class="btn">Guardar Cambios</button>
    </form>
</div>

</body>
</html>
