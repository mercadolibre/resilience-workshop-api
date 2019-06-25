package com.mercadolibre.workshop.v1;

import com.linecorp.armeria.common.MediaType;
import com.mercadolibre.workshop.models.Category;
import com.mercadolibre.workshop.models.Item;

import static spark.Spark.get;
import static spark.Spark.port;

public class App {
    public static void main(String[] args) {
        port(8080);

        get("/healthcheck", (request, response) -> "ok");

        get("/items/:id", (request, response) -> {
            final String itemId = request.params("id");
            final Item item = ItemsService.getInstance().getItem(itemId).get();
            response.type(MediaType.JSON.type());
            return Utils.MAPPER.writeValueAsBytes(item);
        });

        get("/categories/:id", (request, response) -> {
            final String categoryId = request.params("id");
            final Category category = CategoriesService.getInstance().getCategory(categoryId).get();
            response.type(MediaType.JSON.type());
            return Utils.MAPPER.writeValueAsBytes(category);
        });
    }
}
