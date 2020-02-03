package com.rl.netty.server.model.request;

public class RequestObject {

    private RequestContext requestContext;
    private String content;

    public RequestContext getRequestContext() {
        return requestContext;
    }

    public void setRequestContext(final RequestContext requestContext) {
        this.requestContext = requestContext;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }
}
