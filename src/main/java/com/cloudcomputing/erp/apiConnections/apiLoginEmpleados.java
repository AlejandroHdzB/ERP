package com.cloudcomputing.erp.apiConnections;

import com.cloudcomputing.erp.configurations.ApiAccess;
import com.google.gson.Gson;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Named
@RequestScoped
public class ApiLoginEmpleados {
    public static RespuestaLoginEmpleados consultar(String email, String password) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String jsonBody = String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(ApiAccess.URL_BASE_API+"/ValidacionUsuarios/api/login_empleados"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                RespuestaLoginEmpleados respuestaLogin = gson.fromJson(response.body(), RespuestaLoginEmpleados.class);
                return respuestaLogin;
            } else {
                // Error en la respuesta
                System.err.println("Error en la respuesta: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            // Error durante la ejecución
            System.err.println("Error en la ejecuccion: " + e.getMessage());
            return null;
        }
    }
}
