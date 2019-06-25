package com.mercadolibre.workshop.v5;

import com.mercadolibre.workshop.models.Category;
import com.mercadolibre.workshop.models.FullItem;
import com.mercadolibre.workshop.models.Item;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class ItemsService {
    private ItemsService() {
    }

    static ItemsService getInstance() {
        return Holder.INSTANCE;
    }

    CompletableFuture<FullItem> getItem(String id) {
        return Utils
                .doRequest("/items/" + id)
                .thenApply(response -> {
                    try {
                        return Utils.MAPPER.readValue(response.getResponseBodyAsBytes(), Item.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .thenCompose(item -> CategoriesService
                        .getInstance()
                        .getCategory(item.getId())
                        .exceptionally(throwable -> new Category(item.getId()))
                        .thenApply(category -> FullItem.from(item, category))
                );
    }

    private static class Holder {
        static final ItemsService INSTANCE = new ItemsService();
    }
}
