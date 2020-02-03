package com.rl.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUriValidator {

    /**
     * @implNote this regex: '^[/][a-z]{1,}$' is allowing:
     * '/users'
     */
    public static void validatePutURI(final String uri) {
        if (!uri.matches("^[/][a-z]{1,}$")) {
            throw new IllegalArgumentException("The given path is not supported!");
        }
    }

    /**
     * @implNote this regex: '^[/][a-z]{1,}[/][0-9]{1,}$' is allowing:
     * '/users'
     */
    public static void validatePostURI(final String uri) {
        if (!uri.matches("^[/][a-z]{1,}[/][0-9]{1,}$")) {
            throw new IllegalArgumentException("The given path is not supported!");
        }
    }

    /**
     * @implNote this regex: '^[/][a-z]{1,}[/][0-9]{1,}$' is allowing:
     * '/users/123'
     */
    public static void validateDeleteURI(final String uri) {
        if (!uri.matches("^[/][a-z]{1,}[/][0-9]{1,}$")) {
            throw new IllegalArgumentException("The given path is not supported!");
        }
    }

    /**
     * @implNote this regex: '^[/][a-z]{1,}(($)|([/][0-9]{1,}$)|([\?]([a-zA-Z0-9]+[\=]+[a-zA-Z0-9]+&{0,1})+$))' is allowing:
     * '/users'
     * '/users/123'
     * '/users?param1=value1&param2=value2'
     */
    public static void validateGetURI(final String uri) {
        if (!uri.matches("^[/][a-z]{1,}(($)|([/][0-9]{1,}$)|([\\?]([a-zA-Z0-9]+[\\=]+[a-zA-Z0-9 @.-]+&{0,1})+$))")) {
            throw new IllegalArgumentException("The given path is not supported!");
        }
    }
}
