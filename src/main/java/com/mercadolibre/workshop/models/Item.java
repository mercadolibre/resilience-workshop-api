package com.mercadolibre.workshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Item {
    @JsonProperty("id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category_id")
    private String categoryId;
}
