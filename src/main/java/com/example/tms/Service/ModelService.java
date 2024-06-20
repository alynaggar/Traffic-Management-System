package com.example.tms.Service;

import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Repository.CameraRepository;
import com.example.tms.WebSocket.SocketService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ModelService {

    private final RestTemplate restTemplate;
    private final SocketService socketService;
    private final CameraRepository cameraRepository;
    @Value("${TRAFFIC_MODEL_API}")
    private String trafficModelApi;
    @Value("${ACCIDENT_MODEL_API}")
    private String accidentModelApi;
    @Value("${EMERGENCY_AUDIO_MODEL_API}")
    private String emergencyAudioModelApi;

    public ModelService(RestTemplate restTemplate, SocketService socketService, CameraRepository cameraRepository) {
        this.restTemplate = restTemplate;
        this.socketService = socketService;
        this.cameraRepository = cameraRepository;
    }

    public CustomResponseEntity<?> trafficPredict(String imagePath) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("path", imagePath.trim());

        String requestBody = requestJson.toString();

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(trafficModelApi, request, String.class);

        JSONObject jsonObject = new JSONObject(response); // Parse the JSON response string into a JSON object
        String prediction = jsonObject.getString("prediction"); // Extract the "prediction" attribute from the JSON object

        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, prediction);
    }

    public CustomResponseEntity<?> accidentPredict(String imagePath) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("path", imagePath.trim());

        String requestBody = requestJson.toString();

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(accidentModelApi, request, String.class);

        JSONObject jsonObject = new JSONObject(response); // Parse the JSON response string into a JSON object
        String prediction = jsonObject.getString("prediction"); // Extract the "prediction" attribute from the JSON object

        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, prediction);
    }

    public CustomResponseEntity<?> emergencyAudioPredict(String imagePath) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject requestJson = new JSONObject();
        requestJson.put("path", imagePath.trim());

        String requestBody = requestJson.toString();

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(emergencyAudioModelApi, request, String.class);

        JSONObject jsonObject = new JSONObject(response); // Parse the JSON response string into a JSON object
        String prediction = jsonObject.getString("prediction"); // Extract the "prediction" attribute from the JSON object

        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS, prediction);
    }

}
