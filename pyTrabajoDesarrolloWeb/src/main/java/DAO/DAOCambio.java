/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import modelo.Cambio;

/**
 *
 * @author Usuario
 */
public class DAOCambio {
    private String baseCode;
    private Map<String, Double> rates;

    public DAOCambio(){
        
    }
    
    public DAOCambio(String apiEndpoint) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiEndpoint))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        parseApiResponse(response.body());
    }

    private void parseApiResponse(String apiResponse) {
        Gson gson = new Gson();
        Map<String, Object> datos = gson.fromJson(apiResponse, Map.class);

        this.baseCode = (String) datos.get("base_code");
        this.rates = (Map<String, Double>) datos.get("rates");
    }

    public Double getCambio(String currencyCode) {
        return rates.get(currencyCode);
    }
    
    public void CargarCambio(Cambio c){
        try {
            String API = "https://open.er-api.com/v6/latest/USD";
            DAOCambio TipoCambio = new DAOCambio(API);
            
            c.setPEN(TipoCambio.getCambio("PEN"));
            
            
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
