package com.example.tms.Service;

import com.example.tms.Entity.Camera;
import com.example.tms.Entity.Location;
import com.example.tms.Entity.NotificationMessage;
import com.example.tms.Entity.Response.CustomResponseCode;
import com.example.tms.Entity.Response.CustomResponseEntity;
import com.example.tms.Repository.CameraRepository;
import com.example.tms.Repository.LocationRepository;
import com.example.tms.WebSocket.SocketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

        public CustomResponseEntity<?> calculateTraffic() {
            List<Location> locations = locationRepository.findAll();

            ExecutorService executorService = Executors.newFixedThreadPool(5);

            List<Future<?>> futures = new ArrayList<>();

            for (Location location : locations) {
                Future<?> future = executorService.submit(() -> processLocation(location));
                futures.add(future);
            }

            // Wait for all tasks to complete
            for (Future<?> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            executorService.shutdown();

            return new CustomResponseEntity<>(CustomResponseCode.SUCCESS);
        }

        @Transactional
        protected void processLocation(Location location) {
            ArrayList<String> statues = new ArrayList<>();
            String emergency = (String) modelService.emergencyAudioPredict(location.getUrl()).getData();
            if (emergency.equals("ambulance") || emergency.equals("fire truck")) {
                // EMERGENCY IMAGE MODEL
                socketService.sendEmergencyMessage(new NotificationMessage(location.getCameras().get(1)));
            }

            ExecutorService cameraExecutor = Executors.newFixedThreadPool(5);
            List<Future<?>> cameraFutures = new ArrayList<>();

            for (Camera camera : location.getCameras()) {
                Future<?> cameraFuture = cameraExecutor.submit(() -> processCamera(camera, statues));
                cameraFutures.add(cameraFuture);
            }

            // Wait for all camera tasks to complete
            for (Future<?> cameraFuture : cameraFutures) {
                try {
                    cameraFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            cameraExecutor.shutdown();

            Timings timings = adjustTrafficLightTimings(statues);
            socketService.sendTrafficMessage(new NotificationMessage(location, statues, timings.getGreenTimes(), timings.getRedTimes()));
        }

        @Transactional
        protected void processCamera(Camera camera, ArrayList<String> statues) {
            String path = downloadImageWithRetries(camera.getUrl(), 3);
            if (path.isEmpty()) {
                System.out.println("Failed to download image from: " + camera.getUrl());
                statues.add("Low");
                return;
            }
            System.out.println(path);
            String traffic = (String) modelService.trafficPredict(path).getData();
            String accident = (String) modelService.accidentPredict(path).getData();
            if (accident.equals("Accident")) {
                socketService.sendAccidentMessage(new NotificationMessage(camera));
            }
            synchronized (statues) {
                statues.add(traffic);
            }
        }

        public String downloadImageWithRetries(String imageUrl, int maxRetries) {
            int attempt = 0;
            while (attempt < maxRetries) {
                String path = downloadImage(imageUrl);
                if (!path.isEmpty()) {
                    return path;
                }
                attempt++;
            }
            return "";
        }

        public String downloadImage(String imageUrl) {
            String destinationFile = "C:\\Users\\Ali\\Downloads\\tms\\";
            String savedFile = destinationFile + System.currentTimeMillis() + ".jpeg";
            String[] command = {
                    "powershell",
                    "-Command",
                    "Invoke-WebRequest -Uri \"" + imageUrl + "\" -OutFile \"" + savedFile + "\""
            };

            // Initialize the ProcessBuilder with the command list
            ProcessBuilder processBuilder = new ProcessBuilder(command);

            // Optionally, redirect error stream to the standard output stream
            processBuilder.redirectErrorStream(true);

            try {
                // Start the process
                Process process = processBuilder.start();

                // Wait for the process to complete
                int exitCode = process.waitFor();

                // Check if the process completed successfully
                if (exitCode == 0) {
                    return savedFile;
                } else {
                    return "";
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return "";
            }
        }

        public static Timings adjustTrafficLightTimings(ArrayList<String> statuses) {

            int TRAFFIC_CYCLE_TIME;

            // Step 1: Assign weights based on statuses
            int[] weights = new int[statuses.size()];
            for (int i = 0; i < statuses.size(); i++) {
                weights[i] = getStatusWeight(statuses.get(i));
            }

            // Step 2: Calculate total weight
            int totalWeight = Arrays.stream(weights).sum();

            // Step 3: Choose traffic cycle time
            if (totalWeight < 8) {
                TRAFFIC_CYCLE_TIME = 100;
            } else if (totalWeight < 12) {
                TRAFFIC_CYCLE_TIME = 200;
            } else {
                TRAFFIC_CYCLE_TIME = 300;
            }

            // Step 4: Calculate initial green times based on weights
            double[] greenTimes = new double[statuses.size()];
            for (int i = 0; i < statuses.size(); i++) {
                greenTimes[i] = ((double) weights[i] / totalWeight) * TRAFFIC_CYCLE_TIME;
                // Step 5: Apply minimum and maximum green time constraints
                greenTimes[i] = Math.max(MIN_GREEN_TIME, Math.min(MAX_GREEN_TIME, greenTimes[i]));
            }

            // Step 6: Calculate total green time and normalize if necessary
            double totalGreenTime = Arrays.stream(greenTimes).sum();
            if (totalGreenTime != TRAFFIC_CYCLE_TIME) {
                double adjustmentFactor = TRAFFIC_CYCLE_TIME / totalGreenTime;
                for (int i = 0; i < greenTimes.length; i++) {
                    greenTimes[i] *= adjustmentFactor;
                }
            }

            // Step 7: Calculate red times for each street
            double[] redTimes = new double[statuses.size()];
            for (int i = 0; i < statuses.size(); i++) {
                redTimes[i] = Arrays.stream(greenTimes).sum() - greenTimes[i];
            }

            // Convert double arrays to int arrays
            int[] finalGreenTimes = Arrays.stream(greenTimes).mapToInt(g -> (int) Math.round(g)).toArray();
            int[] finalRedTimes = Arrays.stream(redTimes).mapToInt(r -> (int) Math.round(r)).toArray();

            // Return new timings
            return new Timings(finalGreenTimes, finalRedTimes);
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


