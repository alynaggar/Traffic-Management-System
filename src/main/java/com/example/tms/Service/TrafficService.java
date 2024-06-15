package com.example.tms.Service;

import com.example.tms.Entity.Camera;
import com.example.tms.Entity.Location;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Entity.TrafficMessage;
import com.example.tms.Repository.CameraRepository;
import com.example.tms.Repository.LocationRepository;
import com.example.tms.Service.ModelService;
import com.example.tms.WebSocket.SocketService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TrafficService {

    public LocationRepository locationRepository;
    public CameraRepository cameraRepository;
    public ModelService modelService;
    public SocketService socketService;

    public TrafficService(LocationRepository locationRepository, CameraRepository cameraRepository, ModelService modelService, SocketService socketService) {
        this.locationRepository = locationRepository;
        this.cameraRepository = cameraRepository;
        this.modelService = modelService;
        this.socketService = socketService;
    }

    // Minimum and maximum green times
    private static final int MIN_GREEN_TIME = 20;
    private static final int MAX_GREEN_TIME = 90;

    // Weights
    private static final int WEIGHT_TRAFFIC_JAM = 4;
    private static final int WEIGHT_HIGH = 3;
    private static final int WEIGHT_LOW = 2;
    private static final int WEIGHT_EMPTY = 1;

    // Traffic Cycle Time
    private static final int TRAFFIC_CYCLE_TIME = 300;

    public CustomResponseEntity<?> calculateTraffic() {
        List<Location> locations = locationRepository.findAll();
        for (Location location: locations
             ) {
            ArrayList<String> statues = new ArrayList<>();
            for (Camera camera: location.getCameras()
                 ) {
                String response = (String) modelService.predict(camera.getUrl()).getData();
                statues.add(response);
            }
            int[] greenTime = adjustTrafficLightTimings(statues);
            TrafficMessage message = new TrafficMessage(location, statues, greenTime);
            socketService.sendTrafficMessage(message);
        }
        return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
    }

    public static int[] adjustTrafficLightTimings(ArrayList<String> statuses) {
        // Step 1: Assign weights based on statuses
        int[] weights = new int[statuses.size()];
        for (int i = 0; i < statuses.size(); i++) {
            weights[i] = getStatusWeight(statuses.get(i));
        }

        // Step 2: Calculate total weight
        int totalWeight = Arrays.stream(weights).sum();

        // Step 3: Calculate initial green times based on weights
        double[] greenTimes = new double[statuses.size()];
        for (int i = 0; i < statuses.size(); i++) {
            greenTimes[i] = ((double) weights[i] / totalWeight) * TRAFFIC_CYCLE_TIME;
            // Step 4: Apply minimum and maximum green time constraints
            greenTimes[i] = Math.max(MIN_GREEN_TIME, Math.min(MAX_GREEN_TIME, greenTimes[i]));
        }

        // Step 5: Calculate total green time and normalize if necessary
        double totalGreenTime = Arrays.stream(greenTimes).sum();
        if (totalGreenTime != TRAFFIC_CYCLE_TIME) {
            double adjustmentFactor = TRAFFIC_CYCLE_TIME / totalGreenTime;
            for (int i = 0; i < greenTimes.length; i++) {
                greenTimes[i] *= adjustmentFactor;
            }
        }

        // Step 6: Calculate red times for each street
        double[] redTimes = new double[statuses.size()];
        for (int i = 0; i < statuses.size(); i++) {
            redTimes[i] = Arrays.stream(greenTimes).sum() - greenTimes[i];
        }

        // Convert double arrays to int arrays
        int[] finalGreenTimes = Arrays.stream(greenTimes).mapToInt(g -> (int) Math.round(g)).toArray();
        int[] finalRedTimes = Arrays.stream(redTimes).mapToInt(r -> (int) Math.round(r)).toArray();

        // Return new timings
        return finalGreenTimes;
    }

    private static int getStatusWeight(String status) {
        switch (status) {
            case "Traffic Jam":
                return WEIGHT_TRAFFIC_JAM;
            case "High":
                return WEIGHT_HIGH;
            case "Low":
                return WEIGHT_LOW;
            case "Empty":
                return WEIGHT_EMPTY;
            default:
                throw new IllegalArgumentException("Unknown status: " + status);
        }
    }

    // Class to hold green and red times
    static class Timings {
        int[] greenTimes;
        int[] redTimes;

        Timings(int[] greenTimes, int[] redTimes) {
            this.greenTimes = greenTimes;
            this.redTimes = redTimes;
        }

        public int[] getGreenTimes() {
            return greenTimes;
        }

        public void setGreenTimes(int[] greenTimes) {
            this.greenTimes = greenTimes;
        }

        public int[] getRedTimes() {
            return redTimes;
        }

        public void setRedTimes(int[] redTimes) {
            this.redTimes = redTimes;
        }
    }
}
