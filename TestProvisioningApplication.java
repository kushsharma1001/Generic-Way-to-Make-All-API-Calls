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
}
