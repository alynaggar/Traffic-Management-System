package com.example.tms.Service;

import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;


@Service
public class ModelService {

    private final RestTemplate restTemplate;
    @Value("${MODEL_API}")
    private String modelApi;

    public ModelService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CustomResponseEntity<?> predict(String imagePath) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(imagePath, headers);
        String response = restTemplate.postForObject(modelApi, request, String.class);
        JSONObject jsonObject = new JSONObject(response);                           // Parse the JSON response string into a JSON object
        String prediction = jsonObject.getString("prediction");                 // Extract the "prediction" attribute from the JSON object
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, prediction);
    }
}
