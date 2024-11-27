package com.cloudcomputing.erp.apiConnections;

import com.cloudcomputing.erp.configurations.ApiAccess;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiValidacionEmpleados {
    public static ResultadoRespuesta consultar(String email, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String jsonBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiAccess.URL_BASE_API + "/ValidacionUsuarios/api/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Respuesta exitosa
                return new ResultadoRespuesta(response.statusCode(), response.body());
            } else {
                // Error en la respuesta
                return new ResultadoRespuesta(response.statusCode(), "Error: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            // Error durante la ejecución
            return new ResultadoRespuesta(500, "Error: " + e.getMessage());
        }
    }
}
