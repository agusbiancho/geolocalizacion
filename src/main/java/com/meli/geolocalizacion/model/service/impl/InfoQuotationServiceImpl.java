package com.meli.geolocalizacion.model.service.impl;

import com.meli.geolocalizacion.model.service.InfoQuotationService;
import com.meli.geolocalizacion.util.Parser;
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
public class InfoQuotationServiceImpl implements InfoQuotationService {

    @Value("${url.quotation-info-base}")
    private String urlInfoQuotationBase;

    @Value("${url.key-quotation}")
    private String keyQuotation;

    @Value("${currency-base}")
    private String currencyBase;

    @Autowired
    private RestTemplate restTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(IPDirectionServiceImpl.class);

    @Override
    public String getInfoQuotation(String currency) {
        String url = urlInfoQuotationBase + "latest?access_key=" + keyQuotation + "&base=" + currency + "&symbols=" + currencyBase;
        try {
            HttpEntity<String> httpEntity = new HttpEntity<>("");
            ResponseEntity<String> response = restTemplate.exchange(url,
                    HttpMethod.GET,
                    httpEntity,
                    String.class);
            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
