package com.mercadolibre.workshop.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.workshop.Config;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static org.asynchttpclient.Dsl.config;

class Utils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static final AsyncHttpClient HTTP_CLIENT = asyncHttpClient(
            config()
                    .setConnectTimeout(1000)
                    .setRequestTimeout(30000)
                    .setMaxConnections(-1)
    );

    public static CompletableFuture<Response> doRequest(String path) {
        return Utils.HTTP_CLIENT
                .prepareGet(Config.getBackendUrl() + path)
                .setHeader("Content-Type", "application/json")
                .execute()
                .toCompletableFuture();
    }
}
