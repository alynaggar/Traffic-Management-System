package com.example.tms;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.example.tms.Entity.Camera;
import com.example.tms.WebSocket.SocketService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TmsApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(TmsApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/*")
                        .allowedOriginPatterns("*") // Replace with your Angular app URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                        .allowedHeaders("*") // Allowed request headers (you can customize this based on your requirements)
                        .allowCredentials(true);
            }
        };
    }

}
