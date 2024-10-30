<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cloudcomputing.erp.dto.EmpleadoDTO" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Empleados</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <style>
        body {
            background-color: #ECF0F1;
            font-family: 'Arial', sans-serif;
        }

        h1 {
            text-align: center;
            margin: 20px 0;
            color: #343a40;
        }

        .container {
            background: #ECF0F1;
        }

        #Tabla_Contenedor {
            margin-top: 1%;
            background: #ECF0F1;
        }

        #example {
            margin: 10px auto;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        th {
            background-color: #2C3E50;
            color: white;
            padding: 10px;
        }

        td {
            background-color: white;
            padding: 10px;
            border-bottom: 1px solid #dee2e6;
        }

        tr:hover td {
            background-color: #e9ecef;
        }

        .dataTables_wrapper {
            padding: 10px;
            background-color: #ffffff;
            border-radius: 8px;
        }

        .form-group {
            margin-bottom: 10px;
        }

        .form-label {
            font-weight: bold;
        }

        .search-container {
            margin: 0px 0;
            display: flex;
            justify-content: flex-end;
        }

        .search-container input {
            width: 300px;
            margin-left: 10px;
        }

        .btn-accion {
            padding: 5px 10px;
            margin: 2px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            color: #ECF0F1;
            font-size: 14px;
        }

        .btn-nomina {
            background-color: #3498DB; /* Azul Claro */
        }

        .btn-eliminar {
            background-color: #E74C3C; /* Rojo */
        }

        .btn-editar {
            background-color: #2ECC71; /* Verde */
        }
    </style>
</head>
<body>
    <h1>Lista de Empleados</h1>

    <table id="tablaEmpleados" class="display">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Apellido Paterno</th>
                <th>Apellido Materno</th>
                <th>Email</th>
                <th>Teléfono</th>
                <th>Salario</th>
                <th>RFC</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<EmpleadoDTO> empleados = (List<EmpleadoDTO>) request.getAttribute("empleados");

                if (empleados != null && !empleados.isEmpty()) {
                    for (EmpleadoDTO empleado : empleados) {
            %>
            <tr>
                <td><%= empleado.getIdUser() %></td>
                <td><%= empleado.getNombre() %></td>
                <td><%= empleado.getApellidoPaterno() %></td>
                <td><%= empleado.getApellidoMaterno() %></td>
                <td><%= empleado.getEmail() %></td>
                <td><%= empleado.getTelefono() %></td>
                <td><%= empleado.getSalario() %></td>
                <td><%= empleado.getRfc() %></td>
                <td>
                    <button class="btn-accion btn-nomina">Generar Nómina</button>
                    <button class="btn-accion btn-eliminar">Eliminar</button>
                    <button class="btn-accion btn-editar" onclick="editarUsuario('<%= empleado.getIdUser() %>')">Editar</button>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="9" style="text-align: center;">No hay empleados disponibles</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <script>
        $.fn.dataTable.ext.errMode = 'none';
        $(document).ready(function() {
            $('#tablaEmpleados').DataTable({
                paging: true,
                searching: true,
                ordering: true,
                info: true,
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.11.5/i18n/es-ES.json' // Español
                }
            });
        });

        // Función para redirigir a la página de edición
        function editarUsuario(idUser) {
            window.location.href = 'editarEmpleado?idUser=' + idUser;
        }
    </script>
</body>
</html>
