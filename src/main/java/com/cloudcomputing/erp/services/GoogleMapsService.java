package com.cloudcomputing.erp.services;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.IOException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.core5.http.ParseException;

@ApplicationScoped
public class GoogleMapsService {

    private static final String GOOGLE_API_URL = "https://maps.googleapis.com/maps/api/distancematrix/json";
    private static final String API_KEY = "AIzaSyCyj7tOkKINBP4Fepqdy34vD2ZPSiPCJrU"; 
    
public double calcularDistancia(String origen, String destino) throws IOException {
    String url = String.format("%s?origins=%s&destinations=%s&key=%s", GOOGLE_API_URL, origen, destino, API_KEY);

    try (CloseableHttpClient client = HttpClients.createDefault()) {
        HttpGet request = new HttpGet(url);
        try (CloseableHttpResponse response = client.execute(request)) {
            try {
                String json = EntityUtils.toString(response.getEntity());
                return parsearDistancia(json);
            } catch (ParseException e) {
                throw new IOException("Error al parsear la respuesta de la API de Google Maps", e);
            }
        }
    }
}





    private double parsearDistancia(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        JsonNode rows = root.path("rows");
        if (rows.isArray() && rows.size() > 0) {
            JsonNode elements = rows.get(0).path("elements");
            if (elements.isArray() && elements.size() > 0) {
                JsonNode distance = elements.get(0).path("distance");
                return distance.path("value").asDouble(); // Distancia en metros
            }
        }
        throw new IllegalArgumentException("No se pudo calcular la distancia");
    }
}
