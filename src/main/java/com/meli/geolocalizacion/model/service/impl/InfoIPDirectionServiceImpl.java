package com.meli.geolocalizacion.model.service.impl;

import com.meli.geolocalizacion.model.service.InfoIPDirectionService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InfoIPDirectionServiceImpl implements InfoIPDirectionService {

    @Value("${url.ip-direction-info-base}")
    private String urlInfoIPDirectionBase;

    @Value("enabled-circuit-breaker")
    private boolean enabledCircuitBreaker;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(IPDirectionServiceImpl.class);

    @Override
    @CircuitBreaker(name = "circuit-breaker", fallbackMethod = "fallbackInfoIPDirection")
    public String getInfoIPDirection(String ip) {
        HttpEntity<String> httpEntity = new HttpEntity<>("");
        String url = urlInfoIPDirectionBase + "ip?" + ip;
        ResponseEntity<String> response = null;
        /*
         Prueba circuit breaker.
         */
        if(enabledCircuitBreaker) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    httpEntity,
                    String.class);
            return response.getBody();
        } catch (Exception e) {
            throw e;
        }
    }

    public String fallbackInfoIPDirection(Throwable t) {
        LOGGER.error("Error call service InfoIPDirection: " + t.getCause() + " " + t.getMessage());
        return t.getMessage();
    }
}
