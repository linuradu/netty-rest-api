package com.rl.netty.server;

import com.rl.netty.server.model.request.RequestContext;
import com.rl.netty.server.model.request.RequestObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private final static Logger LOG = LoggerFactory.getLogger(HttpServerHandler.class);
    private final static ThreadLocal<RequestContext> localRequestContext = new ThreadLocal<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        if (msg instanceof LastHttpContent) {
            final ByteBuf jsonBuf = ((LastHttpContent) msg).content();
            final RequestObject requestObject = new RequestObject();
            requestObject.setRequestContext(localRequestContext.get());
            requestObject.setContent(jsonBuf.toString(CharsetUtil.UTF_8));

            // removing the value from the thread
            localRequestContext.remove();

            final HttpOrchestrator httpOrchestrator = new HttpOrchestrator();

            logRequest(requestObject);
            final FullHttpResponse response = httpOrchestrator.processRequestObject(requestObject);
            ctx.write(response);

        } else if (msg instanceof HttpRequest) {
            final HttpRequest httpRequest = (HttpRequest) msg;
            final RequestContext requestContext = new RequestContext();
            requestContext.setHttpMethod(httpRequest.method());
            requestContext.setUri(URLDecoder.decode(httpRequest.uri(), StandardCharsets.UTF_8.toString()));
            requestContext.setHeaders(httpRequest.headers().entries().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

            localRequestContext.set(requestContext);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void logRequest(final RequestObject requestObject) {
        LOG.info("Received {} request for the path {}.", requestObject.getRequestContext().getHttpMethod(), requestObject.getRequestContext().getUri());
        if (requestObject.getContent() != null && !requestObject.getContent().isEmpty()) {
            LOG.info("Request payload: {}.", requestObject.getContent());
        }
    }
}