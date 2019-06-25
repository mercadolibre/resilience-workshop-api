package com.mercadolibre.workshop.v1;

import com.mercadolibre.workshop.models.Category;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

class CategoriesService {
    private CategoriesService() {
    }

    public static CategoriesService getInstance() {
        return Holder.INSTANCE;
    }

    public CompletableFuture<Category> getCategory(String id) {
        return Utils
                .doRequest("/categories/" + id)
                .thenApply(response -> {
                    try {
                        return Utils.MAPPER.readValue(response.getResponseBodyAsBytes(), Category.class);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static class Holder {
        static final CategoriesService INSTANCE = new CategoriesService();
    }
}
