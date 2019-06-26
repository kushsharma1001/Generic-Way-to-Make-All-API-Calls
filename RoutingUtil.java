package com.sap.cpi.provisioning.common.util;

import java.net.URI;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RoutingUtil {

    private static Logger log = LoggerFactory.getLogger(RoutingUtil.class);
    private RestTemplate restTemplate = new RestTemplate();

    public <T, V> V call(Request<T> request, Class<V> responseType) {
        try {
            HttpMethod method = request.getMethod();
            HttpEntity<T> requestEntity = prepareRequestEntity(request);

            ResponseEntity<V> responseEntity = restTemplate.exchange(new URI(request.getUri()), method, requestEntity, responseType);

            return responseEntity.getBody();
        } catch (Exception e) {
            final String message = "Error in calling api";
            log.error(message, e);
            e.printStackTrace();
        }
        return null;
    }

    private <T> HttpHeaders buildHttpHeaders(Request<T> request) {
        HttpHeaders requestHeaders = new HttpHeaders();

        if (request.getHeaders().size() != 0) {
            System.out.println("Inside building headers");
            for (Map.Entry<String, Object> entry : request.getHeaders().entrySet()) {
                requestHeaders.add(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return requestHeaders;
    }

    private <T> HttpEntity<T> prepareRequestEntity(Request<T> request) {
        try {
            HttpHeaders requestHeaders = buildHttpHeaders(request);
            return new HttpEntity<>(request.getBody(), requestHeaders);
        } catch (Exception e) {
            String errorMsg = "Error in prepareRequestEntity";
            log.error(errorMsg, e);
        }
        return null;
    }
}
