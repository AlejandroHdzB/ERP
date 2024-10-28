<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cloudcomputing.erp.dto.EmpleadoDTO" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lista de Empleados</title>

    <!-- Estilos y scripts de DataTables -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            font-family: Arial, sans-serif;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #2C3E50;
        }

        th {
            background-color: #2C3E50; /* Azul Marino */
            color: #ECF0F1;           /* Gris Oscuro */
            font-weight: bold;
        }

        tr:nth-child(even) {
            background-color: #ECF0F1; /* Gris Oscuro para filas alternadas */
        }

        tr:hover {
            background-color: #BDC3C7; /* Un tono más claro para hover */
        }

        td {
            color: #2C3E50; /* Azul Marino para el texto de las celdas */
        }

        table.display thead tr {
            border-bottom: 2px solid #2C3E50; /* Borde inferior más grueso en el encabezado */
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
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="8" style="text-align: center;">No hay empleados disponibles</td>
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
                },
                error: false
            });
        });
    </script>
</body>
</html>
