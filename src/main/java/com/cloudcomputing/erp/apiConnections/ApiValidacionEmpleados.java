package com.cloudcomputing.erp.apiConnections;

import com.cloudcomputing.erp.configurations.ApiAccess;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiValidacionEmpleados {
    public static RespuestaValidacionEmpleados consultar(String email) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String jsonBody = String.format("{\"email\":\"%s\"}", email);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiAccess.URL_BASE_API+"/ValidacionUsuarios/api/validar_email"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Estatus 200");
                Gson gson = new Gson();
                RespuestaValidacionEmpleados respuestaVal = gson.fromJson(response.body(), RespuestaValidacionEmpleados.class);
                return respuestaVal;
            } else {
                System.err.println("Error de respuesta de la API");
                return null;
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
