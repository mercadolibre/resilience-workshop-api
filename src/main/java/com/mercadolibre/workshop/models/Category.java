package com.mercadolibre.workshop.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
