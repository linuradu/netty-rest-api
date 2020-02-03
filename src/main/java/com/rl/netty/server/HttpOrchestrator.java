package com.rl.netty.server;

import com.rl.netty.rest.CrudController;
import com.rl.netty.rest.user.UserController;
import com.rl.netty.server.model.request.RequestContext;
import com.rl.netty.server.model.request.RequestObject;
import io.netty.handler.codec.http.FullHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.rl.netty.server.HttpUriValidator.validateDeleteURI;
import static com.rl.netty.server.HttpUriValidator.validateGetURI;
import static com.rl.netty.server.HttpUriValidator.validatePostURI;
import static com.rl.netty.server.HttpUriValidator.validatePutURI;

public class HttpOrchestrator {

    private final static Logger LOG = LoggerFactory.getLogger(HttpOrchestrator.class);
    private final Map<String, CrudController> controllersMap = new HashMap<>();

    {
        controllersMap.put("/users", new UserController());
    }

    public FullHttpResponse processRequestObject(final RequestObject requestObject) {
        try {
            final RequestContext requestContext = requestObject.getRequestContext();
            final CrudController crudController = resolveControllerByPath(requestContext.getUri());
            requestContext.setUriParams(getUriParams(requestContext.getUri()));

            switch (requestContext.getHttpMethod().name()) {
                case "GET":
                    validateGetURI(requestContext.getUri());
                    final Long objectId = getUriObjectId(requestContext.getUri());
                    return objectId != null ? crudController.get(requestObject, objectId) : crudController.getAll(requestObject);
                case "PUT":
                    validatePutURI(requestContext.getUri());
                    return crudController.create(requestObject);
                case "POST":
                    validatePostURI(requestContext.getUri());
                    return crudController.update(requestObject, getUriObjectId(requestContext.getUri()));
                case "DELETE":
                    validateDeleteURI(requestContext.getUri());
                    return crudController.delete(requestObject, getUriObjectId(requestContext.getUri()));
                default:
                    throw new IllegalArgumentException("The given http method is not supported!");
            }
        } catch (RuntimeException ex) {
            LOG.error("Exception while processing the request: ", ex);
            return HttpResponseHandler.http404(ex.getMessage());
        }
    }

    /**
     * @param uri
     * @return
     * @implNote from the given uri: '/users/123' will be returned the long: 123L.
     */
    private Long getUriObjectId(final String uri) {
        final Pattern pattern = Pattern.compile("^[/][a-z]{1,}[/]");
        final Matcher matcher = pattern.matcher(uri);

        if (matcher.find()) {
            return Long.valueOf(uri.substring(matcher.end()));
        }
        return null;
    }

    /**
     * The received uri should be like: '...?param1=value&param2=value'
     *
     * @param uri the complete uri.
     * @return a map with the uri params.
     */
    private Map<String, String> getUriParams(final String uri) {
        if (uri.contains("?")) {
            final String paramsString = uri.substring(uri.indexOf('?') + 1);

            // in case of duplicated params, will be used la last value
            return Arrays.stream(paramsString.contains("&") ? paramsString.split("&") : new String[]{paramsString})
                    .map(value -> value.split("="))
                    .collect(Collectors.toMap(paramValue -> paramValue[0], paramValue -> paramValue[1], (v1, v2) -> v2));
        }
        return new HashMap<>();
    }

    /**
     * @param currentPath
     * @return
     * @implNote this regex: '^[/][a-z]{1,}(($)|([/][0-9]{1,}$)|([\?]([a-z0-9]+[\=]+[a-z0-9]+&{0,1})+$))' is allowing:
     * '/users'
     * '/users/123'
     * '/users?param1=value1&param2=value2'
     */
    private CrudController resolveControllerByPath(final String currentPath) {
        if (!currentPath.matches("^[/][a-z]{1,}(($)|([/][0-9]{1,}$)|([\\?]([a-zA-Z0-9]+[\\=]+[a-zA-Z0-9 @.-]+&{0,1})+$))")) {
            throw new IllegalArgumentException("The given path is not supported!");
        }

        final Optional<String> matchingKey = controllersMap.keySet().stream()
                .filter(currentPath::startsWith)
                .findFirst();

        return matchingKey.map(controllersMap::get).orElseThrow(() -> new IllegalArgumentException("The given path is not supported!"));
    }
}
