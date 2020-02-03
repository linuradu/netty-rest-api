package com.rl.netty.rest;

import com.rl.netty.server.model.request.RequestObject;
import io.netty.handler.codec.http.FullHttpResponse;

public interface CrudController {

    FullHttpResponse create(final RequestObject requestObject);

    FullHttpResponse update(final RequestObject requestObject, final Long objectId);

    FullHttpResponse delete(final RequestObject requestObject, final Long objectId);

    FullHttpResponse get(final RequestObject requestObject, final Long objectId);

    FullHttpResponse getAll(final RequestObject requestObject);
}
