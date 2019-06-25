package com.mercadolibre.workshop.v4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.HttpStatus;
import com.linecorp.armeria.common.MediaType;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;

public class App {
    private static final Server server = buildServer();

    public static void main(String[] args) {
        server.start().join();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }

    private static Server buildServer() {
        ServerBuilder server = new ServerBuilder().http(8080);

        server.service("/healthcheck", (context, response) -> HttpResponse.of(200));

        server.service("/items/{id}", (context, response) -> {
            String itemId = context.pathParam("id");

            return HttpResponse.from(
                    ItemsService
                            .getInstance()
                            .getItem(itemId)
                            .thenApply(item -> respond(HttpStatus.OK, item))
            );
        });

        server.service("/categories/{id}", (context, response) -> {
            String categoryId = context.pathParam("id");

            return HttpResponse.from(
                    CategoriesService
                            .getInstance()
                            .getCategory(categoryId)
                            .thenApply(category -> respond(HttpStatus.OK, category))
            );
        });

        return server.build();
    }

    private static HttpResponse respond(HttpStatus status, Object content) {
        try {
            return HttpResponse.of(
                    status,
                    MediaType.JSON_UTF_8,
                    Utils.MAPPER.writeValueAsBytes(content)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Could not convert response to JSON", e);
        }
    }
}
