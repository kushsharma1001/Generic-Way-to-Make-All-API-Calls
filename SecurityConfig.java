package com.sap.cpi.provisioning.security;

import java.net.URISyntaxException;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;

import com.sap.cpi.provisioning.common.util.Constants;
import com.sap.cpi.provisioning.common.util.Request;
import com.sap.cpi.provisioning.common.util.RoutingUtil;
import com.sap.cpi.provisioning.domain.OAuthResponseDetail;

public class SecurityConfig {

    private static RoutingUtil routingUtil = new RoutingUtil();

    private static Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    public static String getAccessToken() throws URISyntaxException {

        String subdomain = "component-test-prov2";

        try {
            String url = "https://" + subdomain + ".authentication.sap.hana.ondemand.com" + Constants.GET_OAUTH_TOKEN_ENDPOINT_SUFFIX;

            HttpMethod method = HttpMethod.POST;
            MultiValueMap<String, String> bodyMap = getRequestBody();
            HashMap<String, Object> headersMap = getRequestHeaders();

            Request<MultiValueMap<String, String>> request = new Request<>();
            request.setUri(url);
            request.setMethod(method);
            request.setBody(bodyMap);
            request.setHeaders(headersMap);

            log.info("Attempting api call for : " + request);
            OAuthResponseDetail oauthResponse = routingUtil.call(request, OAuthResponseDetail.class);
            log.info("Api call for request : " + request + " completed successfully");
            log.info("Access token retrieved is: " + oauthResponse.getAccessToken());
            return oauthResponse.getAccessToken();
        } catch (RestClientException e) {
            final String message = "Error in getting Oauth token for subdomain: " + subdomain;
            log.error(message, e);
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static HashMap<String, Object> getRequestHeaders() {
        HashMap<String, Object> headersMap = new HashMap<>();
        headersMap.put("Content-Type", "application/x-www-form-urlencoded");
        headersMap.put("Accept", "application/json;charset=utf8");
        /*
         * headersMap.put("Content-Type", MediaType.APPLICATION_FORM_URLENCODED); headersMap.put("Accept", MediaType.APPLICATION_JSON_UTF8);
         */
        return headersMap;
    }

    private static MultiValueMap<String, String> getRequestBody() {
        MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>(); // send form-urlendcoded body in MultiValueMap
        bodyMap.add(Constants.CLIENT_ID, Constants.XSUAA_CLIENT_ID);
        bodyMap.add(Constants.CLIENT_SECRET, Constants.XSUAA_CLIENT_SECRET);
        bodyMap.add(Constants.GRANT_TYPE, Constants.PASSWORD);
        bodyMap.add(Constants.RESPONSE_TYPE, Constants.RESPONSE_TYPE_TOKEN);
        bodyMap.add(Constants.USERNAME, Constants.USERNAME_VALUE);
        bodyMap.add(Constants.PASSWORD, Constants.PASSWORD_VALUE);
        return bodyMap;
    }
}
