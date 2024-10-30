<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.cloudcomputing.erp.dto.ContabilidadDTO" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <title>Contabilidad</title>
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
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Lista de Transacciones</h1>

            <div class="form-group">
                <label for="consultaDate" class="form-label">Selecciona la Fecha de Consulta</label>
                <input type="date" id="consultaDate" class="form-control">
            </div>
            <div class="form-group">
                <label for="downloadOption" class="form-label">Selecciona una opción para descargar</label>
                <select id="downloadOption" class="form-select">
                    <option value="" disabled selected>Selecciona una opción</option>
                    <option value="excel">Descargar como Excel</option>
                    <option value="pdf">Descargar como PDF</option>
                </select>
            </div>

            <div class="search-container">
                <label for="search" class="form-label">Buscar:</label>
                <input type="search" id="search" class="form-control" placeholder="Buscar en la tabla">
            </div>

            <div id='Tabla_Contenedor'>
                <table id="example" class="display">
                    <thead>
                        <tr>
                            <th>Fecha</th>
                            <th>Descripción</th>
                            <th>Cuenta</th>
                            <th>Haber</th>
                            <th>Debe</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<ContabilidadDTO> trans = (List<ContabilidadDTO>) request.getAttribute("trans");

                            if (trans != null && !trans.isEmpty()) {
                                for (ContabilidadDTO tran : trans) {
                        %>  
                        <tr>
                            <td><%= tran.getFechaMov() %></td>
                            <td><%= tran.getDescripcion() %></td>
                            <td><%= tran.getCuentaMovimiento() %></td>
                            <%
                                // Verifica el tipo de movimiento para determinar si es un debe o un haber
                                if ("venta".equalsIgnoreCase(tran.getTipoMov())) {
                            %>
                                <td><%= tran.getMonto() %></td>
                                <td></td>
                            <%
                                } else if ("compra".equalsIgnoreCase(tran.getTipoMov())) {
                            %>
                                <td></td>
                                <td><%= tran.getMonto() %></td>
                            <%
                                }
                            %>
                        </tr>
                        <%
                                }
                            } else {
                        %>
                        <tr>
                            <td colspan="5" style="text-align: center;">No hay transacciones disponibles</td>
                        </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>

        <script>
            $(window).on('load', function () {
                var table = $('#example').DataTable({
                    "paging": true,
                    "searching": true,
                    "lengthChange": false,
                    "dom": "lrtip"
                });

                $('#search').on('keyup', function () {
                    table.search(this.value).draw();
                });
            });
        </script>
    </body>
</html>
