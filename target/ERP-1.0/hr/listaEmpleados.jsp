<%-- 
    Document   : listEmployees
    Created on : 20 oct. 2024, 22:07:22
    Author     : Alejandro
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pagina empleados</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/2.1.8/css/dataTables.dataTables.min.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/2.1.8/js/dataTables.min.js"></script>
</head>
<body>
    <h1>Mensaje desde el servlet:</h1>
    <p>${mensaje}</p>
    <table id="tablaEmpleados">
        <thead>
            <tr>
                <th>id</th>
                <th>Nombre</th>
                <th>Apellidos</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>1</td>
                <td>Ale</td>
                <td>Buenas</td>
            </tr>
        </tbody>
    </table>
    <script>
        $(document).ready(function() {
            $('#tablaEmpleados').DataTable({
                paging: true,        
                searching: true,     
                ordering: true,      
                info: true,          
                language: {
                    url: '//cdn.datatables.net/plug-ins/1.11.5/i18n/es-ES.json'
                }
            });
        });
    </script>
</body>
</html>

