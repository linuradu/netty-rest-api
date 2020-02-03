package com.rl.netty.server.model.request;

import io.netty.handler.codec.http.HttpMethod;

import java.util.Map;

public class RequestContext {

    private HttpMethod httpMethod;
    private String uri;
    private Map<String, String> uriParams;
    private Map<String, String> headers;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(final HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public Map<String, String> getUriParams() {
        return uriParams;
    }

    public void setUriParams(final Map<String, String> uriParams) {
        this.uriParams = uriParams;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(final Map<String, String> headers) {
        this.headers = headers;
    }
}
