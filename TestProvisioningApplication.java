package com.sap.cpi.provisioning.component;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import com.sap.cpi.provisioning.security.SecurityConfig;

public class TestProvisioningApplication {

    private static Logger log = LoggerFactory.getLogger(TestProvisioningApplication.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testAccessToken() throws URISyntaxException {
        String accessToken = SecurityConfig.getAccessToken();
        Assert.assertFalse(accessToken.isEmpty());
        Assert.assertNotNull(accessToken);
    }

    @Test
    public void testProvision() throws URISyntaxException {
        String accessToken = SecurityConfig.getAccessToken();
        log.info("Access Token for the tenant is: " + accessToken);
        String provAppHostName = "it-stage-app-prov";
        String environment_prod = "prod";
        String provisionUrl = "https://" + provAppHostName + ".cfapps.sap.hana.ondemand.com" + "/cpi-tenant/v1.0/provision?environment=" + environment_prod;
        URI uri = new URI(provisionUrl);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Authorization", "Bearer " + accessToken.replaceAll("\r", "").replaceAll("\n", ""));

        HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.PUT, requestEntity, String.class);

        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
