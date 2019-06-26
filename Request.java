package com.sap.cpi.provisioning.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class Request<T> {

    private T body;

    private Map<String, Object> headers = new HashMap<String, Object>();

    private HttpMethod method;

    private String uri;

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Request [body=" + body + ", headers=" + headers + ", method=" + method + ", uri=" + uri + "]";
    }
}
