package com.cloudcomputing.erp.apiConnections;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Named
@RequestScoped
public class apiLoginEmpleados {
    public void consultar() {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String jsonBody = "{\"email\":\"juan.perez@example.com\",\"password\":password123}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://10.128.1.68/ValidacionUsuarios/api/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Respuesta: " + response.body());
            } else {
                System.err.println("Error: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
