package com.mercadolibre.workshop.v3;

import com.mercadolibre.workshop.models.Item;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class ItemsService {
    private ItemsService() {
    }

    static ItemsService getInstance() {
        return Holder.INSTANCE;
    }

    CompletableFuture<Item> getItem(String id) {
        return Utils
                .doRequest("/items/" + id)
                .thenApply(response -> {
                    try {
                        return Utils.MAPPER.readValue(response.getResponseBodyAsBytes(), Item.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static class Holder {
        static final ItemsService INSTANCE = new ItemsService();
    }
}
